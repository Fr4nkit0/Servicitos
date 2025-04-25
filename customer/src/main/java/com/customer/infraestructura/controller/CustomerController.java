package com.customer.infraestructura.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.commons.dto.request.SaveCustomer;
import com.commons.dto.response.GetAddress;
import com.commons.dto.response.GetCustomerDetail;
import com.customer.application.dto.request.UpdateAddress;
import com.customer.application.dto.request.UpdateCustomer;
import com.customer.application.dto.response.GetCustomer;
import com.customer.application.exceptions.CustomerNotFoundException;
import com.customer.application.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controlador REST para gestionar operaciones relacionadas con clientes.
 * Proporciona endpoints para crear, obtener, actualizar y eliminar información
 * de clientes.
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Obtiene una lista paginada de todos los clientes.
     *
     * @param pageable Parámetros de paginación y ordenamiento.
     * @return Página de clientes activos.
     */
    @GetMapping
    public ResponseEntity<Page<GetCustomer>> findAll(Pageable pageable) {
        return ResponseEntity.ok(customerService.findAll(pageable));
    }

    /**
     * Busca un cliente por su identificador único.
     *
     * @param id ID del cliente.
     * @return Datos del cliente encontrado.
     */

    @GetMapping("/{id}")
    public ResponseEntity<GetCustomer> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    /**
     * Busca un cliente con todos sus datos por su identificador único.
     *
     * @param id ID del cliente.
     * @return Datos del cliente encontrado.
     */

    @GetMapping("/{id}/detail")
    public ResponseEntity<GetCustomerDetail> findByIdCustomerDetail(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findByIdCustomerDetail(id));
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<GetAddress> findByIdAddress(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findByIdAddress(id));
    }

    /**
     * Crea un nuevo cliente activo en el sistema.
     *
     * @param saveCustomer Objeto con los datos necesarios para crear el cliente.
     * @return Cliente creado.
     */

    @PostMapping
    public ResponseEntity<GetCustomer> save(@RequestBody @Valid SaveCustomer saveCustomer, HttpServletRequest request) {
        GetCustomer createdCustomer = customerService.save(saveCustomer);
        String baseURL = request.getRequestURL().toString();
        URI newLocation = URI.create(baseURL + "/" + createdCustomer.id());
        return ResponseEntity.created(newLocation).body(createdCustomer);
    }

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param updateCustomer Datos actualizados del cliente.
     * @param id             ID del cliente a actualizar.
     * @return Cliente actualizado.
     */

    @PatchMapping("/{id}")
    public ResponseEntity<GetCustomer> updateById(@RequestBody @Valid UpdateCustomer updateCustomer,
            @PathVariable Long id) {
        return ResponseEntity.ok(customerService.updateById(updateCustomer, id));
    }

    /**
     * Actualiza la dirección de un cliente existente.
     *
     * @param updateAddress Datos actualizados de la dirección.
     * @param id            ID del cliente cuya dirección se actualiza.
     * @return Dirección actualizada.
     */

    @PatchMapping("/{id}/address")
    public ResponseEntity<GetAddress> updateAddressById(@RequestBody @Valid UpdateAddress updateAddress,
            @PathVariable Long id) {
        return ResponseEntity.ok(customerService.upadateAddressById(updateAddress, id));
    }

    /**
     * Elimina un cliente del sistema.
     * 
     * Este endpoint realiza una eliminación lógica del cliente, es decir, cambia su
     * estado a inactivo
     * y lo marca como eliminado, sin remover físicamente sus datos de la base de
     * datos.
     *
     * @param id ID del cliente que se desea eliminar.
     * @return Respuesta vacía con estado 204 si la operación fue exitosa.
     * @throws CustomerNotFoundException si no se encuentra un cliente con el ID
     *                                   proporcionado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        customerService.deleteById(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

}
