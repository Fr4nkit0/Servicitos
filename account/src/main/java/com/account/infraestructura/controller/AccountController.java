package com.account.infraestructura.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.account.application.dto.request.Deposito;
import com.account.application.dto.request.SaveAccount;
import com.account.application.dto.response.FullAccountInfo;
import com.account.application.dto.response.GetAccount;
import com.account.application.service.AccountService;

import jakarta.validation.Valid;

/**
 * Controlador REST encargado de gestionar operaciones sobre cuentas.
 * Proporciona endpoints para crear, consultar, modificar y eliminar cuentas.
 */
@Controller
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Obtiene una lista paginada de todas las cuentas existentes.
     *
     * @param pageable Objeto con información de paginación (página, tamaño,
     *                 ordenamiento).
     * @return Página de cuentas {@link GetAccount}.
     */
    @GetMapping
    public ResponseEntity<Page<GetAccount>> findAll(Pageable pageable) {
        return ResponseEntity.ok(accountService.findAll(pageable));
    }

    /**
     * Crea una nueva cuenta bancaria junto con un nuevo cliente.
     *
     * @param saveAccount DTO con los datos necesarios para registrar cuenta y
     *                    cliente.
     * @return DTO {@link FullAccountInfo} con los datos completos de la cuenta y
     *         cliente.
     */
    @PostMapping
    public ResponseEntity<FullAccountInfo> addAccount(@Valid @RequestBody SaveAccount saveAccount) {
        return ResponseEntity.ok(accountService.addAccount(saveAccount));
    }

    /**
     * Realiza un depósito en una cuenta existente.
     *
     * @param deposito DTO con el número de cuenta y el monto a depositar.
     * @return DTO {@link GetAccount} con el estado actualizado de la cuenta.
     */
    @PatchMapping
    public ResponseEntity<GetAccount> depositInAccount(@Valid @RequestBody Deposito deposito) {
        return ResponseEntity.ok(accountService.depositInAccount(deposito));
    }

    /**
     * Busca una cuenta por su número.
     * 
     * @param number Número de cuenta a buscar.
     * @return DTO {@link GetAccount} con los datos de la cuenta encontrada.
     */
    @GetMapping("/by-number")
    public ResponseEntity<GetAccount> findByNumber(@RequestParam String number) {
        return ResponseEntity.ok(accountService.findByNumber(number));
    }

    /**
     * Elimina una cuenta a partir de su número.
     *
     * @param number Número de cuenta a eliminar.
     * @return 204 No Content si la eliminación fue exitosa.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteByNumber(@RequestParam String number) {
        accountService.deleteByNumber(number);
        return ResponseEntity.noContent().build();
    }
}
