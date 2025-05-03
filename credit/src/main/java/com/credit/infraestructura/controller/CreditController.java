package com.credit.infraestructura.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.credit.application.dto.request.SaveCredit;
import com.credit.application.dto.response.GetCredit;
import com.credit.application.service.CreditService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controlador REST para gestionar operaciones relacionadas con créditos.
 * Proporciona endpoints para registrar, buscar y eliminar créditos.
 */
@RequestMapping("/credits")
@RestController
public class CreditController {

    private final CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    /**
     * Obtiene una lista paginada de créditos existentes.
     *
     * @param pageable objeto con información de paginación.
     * @return una página de créditos en formato DTO.
     */

    @GetMapping
    public ResponseEntity<Page<GetCredit>> findAll(Pageable pageable) {
        return ResponseEntity.ok(creditService.findAll(pageable));
    }

    /**
     * Busca un crédito por su ID.
     *
     * @param id identificador del crédito.
     * @return DTO con los datos del crédito.
     */

    @GetMapping("/{id}")
    public ResponseEntity<GetCredit> findById(@PathVariable Long id) {
        return ResponseEntity.ok(creditService.findById(id));
    }

    /**
     * Registra un nuevo crédito y realiza un depósito en la cuenta correspondiente.
     *
     * @param saveCredit DTO con la información del crédito a registrar.
     * @return DTO del crédito registrado.
     */
    @PostMapping
    public ResponseEntity<GetCredit> registerCredit(SaveCredit saveCredit, HttpServletRequest request) {
        GetCredit savedCredit = creditService.registerCredit(saveCredit);
        String requestUrl = request.getRequestURL().toString();
        URI location = URI.create(requestUrl + "/" + savedCredit.id());
        return ResponseEntity.created(location).body(savedCredit);
    }

    /**
     * Elimina lógicamente un crédito.
     *
     * @param id identificador del crédito a eliminar.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        creditService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
