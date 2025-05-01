package com.account.application.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import com.account.application.client.CustomerRestClient;
import com.account.application.dto.request.SaveAccount;
import com.account.application.dto.response.FullAccountInfo;
import com.account.application.dto.response.GetAccount;
import com.account.application.exceptions.AccountPersistenceException;
import com.account.application.exceptions.CustomerCreationException;
import com.account.application.exceptions.CustomerServiceException;
import com.account.application.mapper.AccountMapper;
import com.account.application.service.AccountService;
import com.account.domain.persistence.Account;
import com.account.domain.repository.AccountRepository;
import com.commons.dto.request.Deposito;
import com.commons.dto.response.GetCustomerDetail;

/**
 * Implementación del servicio de cuentas. Encapsula la lógica de negocio para
 * la gestión de cuentas, incluyendo la creación, obtención, y
 * eliminación lógica.
 *
 * Utiliza un cliente REST para interactuar con el servicio de clientes,
 * y un repositorio JPA para operaciones con la base de datos.
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRestClient restClient;

    /**
     * Constructor que inyecta las dependencias requeridas.
     *
     * @param accountRepository repositorio de cuentas
     * @param restClient        cliente REST para comunicación con el servicio de
     *                          clientes
     */

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRestClient restClient) {
        this.accountRepository = accountRepository;
        this.restClient = restClient;
    }

    /**
     * Obtiene todas las cuentas activas de forma paginada.
     *
     * @param pageable objeto de paginación y orden
     * @return página de cuentas mapeadas al DTO GetAccount
     */

    @Transactional(readOnly = true)
    @Override
    public Page<GetAccount> findAll(Pageable pageable) {
        return accountRepository.findActiveAll(pageable).map(AccountMapper::toGetDto);
    }

    /**
     * Crea una nueva cuenta asociada a un cliente. Este proceso incluye:
     * 
     * La validación de los datos de entrada.
     * La creación del cliente a través de un servicio externo.
     * La creación y persistencia de la cuenta en la base de datos.
     * 
     *
     * En caso de que el cliente ya exista, se recomienda validar previamente fuera
     * de este método,
     * ya que aquí se asume la creación de un nuevo cliente.
     *
     * @param saveAccount Objeto DTO que contiene los datos requeridos para
     *                    registrar
     *                    tanto al cliente como la cuenta asociada.
     * @return Objeto {@link FullAccountInfo} con la información completa de la
     *         cuenta creada
     *         y los detalles del cliente recién registrado.
     *
     * @throws IllegalArgumentException    Si los datos de entrada son nulos o
     *                                     incompletos.
     * @throws CustomerCreationException   Si la creación del cliente falla por
     *                                     parte del servicio externo,
     *                                     incluyendo errores HTTP o respuestas
     *                                     inesperadas.
     * @throws CustomerServiceException    Si ocurre un error al comunicarse con el
     *                                     servicio externo
     *                                     o si la respuesta del cliente es
     *                                     inválida.
     * @throws AccountPersistenceException Si ocurre un error al guardar la cuenta
     *                                     en la base de datos.
     */

    @Override
    public FullAccountInfo addAccount(SaveAccount saveAccount) {
        try {
            // 1. Validación de entrada robusta
            if (saveAccount == null) {
                throw new IllegalArgumentException("SaveAccount no puede ser nulo");
            }
            if (saveAccount.customer() == null) {
                throw new IllegalArgumentException("Customer data es requerido");
            }
            // 2. Creación del cliente con manejo de null seguro
            ResponseEntity<GetCustomerDetail> createdCustomer = restClient.save(saveAccount.customer());

            // 3. Manejo de respuestas HTTP con Optional para evitar NPE
            HttpStatusCode statusCode = createdCustomer.getStatusCode();
            if (!statusCode.is2xxSuccessful()) {
                String errorDetails = Optional.ofNullable(createdCustomer.getBody())
                        .map(Object::toString)
                        .orElse("Sin detalles del error");

                throw new CustomerCreationException(
                        String.format("Error creando cliente - Código: %d - Detalles: %s",
                                statusCode.value(),
                                errorDetails));
            }

            // 4. Validación del cuerpo de respuesta con mensaje claro
            GetCustomerDetail customer = createdCustomer.getBody();
            if (customer == null) {
                throw new CustomerServiceException("Respuesta inválida del servicio de clientes: cuerpo vacío");
            }
            if (customer.id() == null) {
                throw new CustomerServiceException("El cliente creado no tiene ID válido");
            }

            // 5. Persistencia con verificación de resultado
            Account accountToSave = AccountMapper.toEntityFromDto(saveAccount, customer.id());
            Account savedAccount = accountRepository.save(accountToSave);
            return AccountMapper.toDtoFromEntity(savedAccount, customer);

        } catch (DataAccessException ex) {
            // Loggear excepción completa para debugging
            throw new AccountPersistenceException("Error crítico al guardar datos", ex);
        } catch (RestClientException ex) {
            throw new CustomerServiceException("Error en servicio externo", ex);
        }
    }

    /**
     * Realiza un depósito en una cuenta. Este método busca la cuenta por su número
     * y actualiza su saldo.
     *
     * @param deposito DTO que contiene el número de cuenta y el monto a depositar
     * @return DTO con la información de la cuenta después del depósito
     * @throws RuntimeException si no se encuentra la cuenta o si ocurre un error
     *                          al actualizar el saldo
     */

    @Override
    public GetAccount depositInAccount(Deposito deposito) {
        Account account = findByAccountNumber(deposito.accountNumber());
        account.setBalance(account.getBalance().add(deposito.amount()));
        return AccountMapper.toGetDto(accountRepository.save(account));
    }

    /**
     * Busca una cuenta activa por su número de cuenta.
     *
     * @param number número de cuenta
     * @return DTO con los datos de la cuenta encontrada
     * @throws RuntimeException si no se encuentra la cuenta
     */

    @Transactional(readOnly = true)
    @Override
    public GetAccount findByNumber(String number) {
        return AccountMapper.toGetDto(findByAccountNumber(number));
    }

    /**
     * Realiza una eliminación lógica de una cuenta, marcándola como inactiva
     * y registrando la fecha de eliminación.
     *
     * @param number número de cuenta a eliminar
     * @throws RuntimeException si no se encuentra la cuenta
     */
    @Override
    public void deleteByNumber(String number) {
        Account account = findByAccountNumber(number);
        account.setDeletedAt(LocalDateTime.now());
        account.setActive(false);
        accountRepository.save(account);

    }

    /**
     * Método privado auxiliar para buscar una cuenta por número.
     *
     * @param accountNumber número de cuenta
     * @return entidad de cuenta
     * @throws RuntimeException si no se encuentra la cuenta
     */
    private Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

}
