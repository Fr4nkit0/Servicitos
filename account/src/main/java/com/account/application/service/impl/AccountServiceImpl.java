package com.account.application.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.account.application.client.CustomerRestClient;
import com.account.application.dto.request.SaveAccount;
import com.account.application.dto.response.FullAccountInfo;
import com.account.application.dto.response.GetAccount;
import com.account.application.mapper.AccountMapper;
import com.account.application.service.AccountService;
import com.account.domain.persistence.Account;
import com.account.domain.repository.AccountRepository;
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
     * Crea una nueva cuenta asociada a un cliente, creando primero el cliente
     * mediante una llamada al servicio externo de customer.
     *
     * @param saveAccount DTO con los datos necesarios para crear la cuenta y el
     *                    cliente
     * @return DTO con la información completa de la cuenta y del cliente
     * @throws RuntimeException si no se puede crear el cliente o no se recibe
     *                          respuesta válida
     */

    @Override
    public FullAccountInfo addAccount(SaveAccount saveAccount) {
        ResponseEntity<GetCustomerDetail> createdCustomer = restClient.save(saveAccount.customer());
        if (createdCustomer.getStatusCode().is2xxSuccessful()) {
            GetCustomerDetail customer = createdCustomer.getBody();
            if (customer == null) {
                throw new RuntimeException("Customer not found");
            }
            Account createdAccount = AccountMapper.toEntityFromDto(saveAccount, customer.id());
            return AccountMapper.toDtoFromEntity(accountRepository.save(createdAccount), customer);
        }
        return null;
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
