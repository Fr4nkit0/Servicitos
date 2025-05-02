package com.credit.application.service.impl;

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

import com.commons.dto.response.GetAccount;
import com.credit.application.client.AccountRestClient;
import com.credit.application.dto.request.SaveCredit;
import com.credit.application.dto.response.GetCredit;
import com.credit.application.exceptions.AccountDepositException;
import com.credit.application.exceptions.AccountServiceException;
import com.credit.application.exceptions.CreditNotFoundException;
import com.credit.application.exceptions.CreditPersistenceException;
import com.credit.application.mapper.CreditMapper;
import com.credit.application.service.CreditService;
import com.credit.domain.persistence.Credit;
import com.credit.domain.repository.CreditRepository;

/**
 * Implementación del servicio de créditos.
 * 
 * Esta clase gestiona operaciones como registro, listado, búsqueda y
 * eliminación lógica de créditos.
 * También se comunica con el servicio externo de cuentas para realizar
 * depósitos.
 */
@Service
@Transactional
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final AccountRestClient accountRestClient;

    /**
     * Constructor que inyecta dependencias.
     *
     * @param creditRepository  Repositorio de créditos.
     * @param accountRestClient Cliente REST para el servicio de cuentas.
     */

    public CreditServiceImpl(CreditRepository creditRepository, AccountRestClient accountRestClient) {
        this.creditRepository = creditRepository;
        this.accountRestClient = accountRestClient;
    }

    /**
     * Obtiene todos los créditos activos en forma paginada.
     *
     * @param pageable Información de paginación.
     * @return Página de créditos en formato DTO.
     */

    @Transactional(readOnly = true)
    @Override
    public Page<GetCredit> findAll(Pageable pageable) {
        return creditRepository.findAll(pageable)
                .map(CreditMapper::toGetDto);
    }

    /**
     * Busca un crédito por su ID.
     *
     * @param id Identificador del crédito.
     * @return Crédito encontrado en formato DTO.
     * @throws CreditNotFoundException si no se encuentra el crédito .
     */

    @Transactional(readOnly = true)
    @Override
    public GetCredit findById(Long id) {
        return CreditMapper.toGetDto(findByIdEntity(id));
    }

    /**
     * Registra un nuevo crédito, realiza un depósito en la cuenta correspondiente y
     * guarda el crédito en la base de datos.
     *
     * @param saveCredit DTO con los datos del crédito a registrar.
     * @return Crédito registrado en formato DTO.
     * @throws AccountDepositException    si el servicio de cuentas responde con
     *                                    error al realizar el depósito.
     * @throws AccountServiceException    si hay un problema de comunicación con el
     *                                    servicio de cuentas.
     * @throws CreditPersistenceException si ocurre un error al guardar el crédito.
     */

    @Override
    public GetCredit registerCredit(SaveCredit saveCredit) {
        try {

            // Creación del depósito con manejo de null seguro
            ResponseEntity<GetAccount> responseDepositInAccount = accountRestClient.despositInAccount(
                    CreditMapper.toDeposito(saveCredit));

            // Manejo de respuestas HTTP con Optional para evitar NPE
            HttpStatusCode statusCode = responseDepositInAccount.getStatusCode();
            if (!statusCode.is2xxSuccessful()) {
                String errorDetails = Optional.ofNullable(responseDepositInAccount.getBody())
                        .map(Object::toString)
                        .orElse("Sin detalles del error");

                throw new AccountDepositException(
                        String.format("Error realizando depósito - Código: %d - Detalles: %s",
                                statusCode.value(),
                                errorDetails));
            }

            // Validación del cuerpo de respuesta con mensaje claro
            GetAccount account = responseDepositInAccount.getBody();
            if (account == null) {
                throw new AccountServiceException("Respuesta inválida del servicio de cuentas: cuerpo vacío");
            }

            // Persistencia con verificación de resultado
            Credit creditToSave = CreditMapper.toEntityFromDto(saveCredit);
            Credit savedCredit = creditRepository.save(creditToSave);
            return CreditMapper.toGetDto(savedCredit);

        } catch (DataAccessException ex) {
            throw new CreditPersistenceException("Error crítico al guardar datos del crédito", ex);
        } catch (RestClientException ex) {
            throw new AccountServiceException("Error en servicio externo de cuentas", ex);
        }
    }

    /**
     * Realiza una eliminación lógica del crédito (soft delete).
     *
     * @param id Identificador del crédito a eliminar.
     * @throws RuntimeException si no se encuentra el crédito.
     */
    @Override
    public void delete(Long id) {
        Credit deleteCredit = findByIdEntity(id);
        deleteCredit.setDeletedAt(LocalDateTime.now());
        deleteCredit.setActive(false);
    }

    /**
     * Método que busca un crédito por su ID.
     *
     * @param id ID del crédito a buscar.
     * @return El crédito encontrado.
     * @throws CreditNotFoundException si no se encuentra el crédito.
     */
    private Credit findByIdEntity(Long id) {
        return creditRepository.findActiveById(id)
                .orElseThrow(() -> new CreditNotFoundException(id));
    }
}
