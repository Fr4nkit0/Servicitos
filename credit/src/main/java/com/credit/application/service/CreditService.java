package com.credit.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.credit.application.dto.request.SaveCredit;
import com.credit.application.dto.response.GetCredit;

public interface CreditService {
    /**
     * Obtiene una lista paginada de créditos existentes.
     *
     * @param pageable objeto con información de paginación.
     * @return una página de créditos en formato DTO.
     */

    Page<GetCredit> findAll(Pageable pageable);

    /**
     * Busca un crédito por su ID.
     *
     * @param id identificador del crédito.
     * @return DTO con los datos del crédito.
     */
    GetCredit findById(Long id);

    /**
     * Registra un nuevo crédito y realiza un depósito en la cuenta correspondiente.
     *
     * @param saveCredit DTO con la información del crédito a registrar.
     * @return DTO del crédito registrado.
     */

    GetCredit registerCredit(SaveCredit saveCredit);

    /**
     * Elimina lógicamente un crédito.
     *
     * @param id identificador del crédito a eliminar.
     */
    void delete(Long id);

}
