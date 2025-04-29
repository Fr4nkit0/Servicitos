package com.account.application.service;

import com.account.application.dto.request.SaveAccount;
import com.account.application.dto.response.FullAccountInfo;
import com.account.application.dto.response.GetAccount;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz que define las operaciones del servicio de cuentas.
 * Representa la capa de negocio de la aplicación en relación a cuentas
 * bancarias.
 */
public interface AccountService {

    /**
     * Obtiene todas las cuentas activas de forma paginada.
     *
     * @param pageable objeto de paginación y orden
     * @return página de cuentas
     */
    Page<GetAccount> findAll(Pageable pageable);

    /**
     * Crea una nueva cuenta asociada a un cliente.
     *
     * @param saveAccount DTO con los datos necesarios
     * @return DTO con información detallada de la cuenta creada
     */
    FullAccountInfo addAccount(SaveAccount saveAccount);

    /**
     * Busca una cuenta por su número.
     *
     * @param number número de cuenta
     * @return DTO con los datos de la cuenta
     */
    GetAccount findByNumber(String number);

    /**
     * Realiza una eliminación lógica de una cuenta.
     *
     * @param number número de cuenta
     */
    void deleteByNumber(String number);
}
