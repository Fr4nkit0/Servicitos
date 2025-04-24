package com.customer.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.commons.dto.request.SaveCustomer;
import com.commons.dto.response.GetAddress;
import com.customer.application.dto.request.UpdateAddress;
import com.customer.application.dto.request.UpdateCustomer;
import com.customer.application.dto.response.GetCustomer;
import com.customer.application.exceptions.CustomerNotFoundException;

/**
 * Servicio para gestionar las operaciones relacionadas con los clientes
 * (Customer).
 */
public interface CustomerService {
    /**
     * Obtiene una lista paginada de todos los clientes existentes en el sistema.
     *
     * @param pageable Objeto que contiene información de paginación y ordenamiento.
     * @return Página de clientes representados como objetos GetCustomer.
     */
    Page<GetCustomer> findAll(Pageable pageable);

    /**
     * Busca un cliente por su identificador único.
     *
     * Si el cliente no es encontrado, se lanza una excepción de tipo
     * {@link CustomerNotFoundException}.
     *
     * @param id Identificador del cliente.
     * @return Objeto GetCustomer con los datos del cliente encontrado.
     * @throws CustomerNotFoundException Si no se encuentra un cliente con el
     *                                   identificador proporcionado.
     */
    GetCustomer findById(Long id);

    /**
     * Busca una Dirrecion de un Cliente por su indentifacor unico.
     * Si el cliente no es encontrado, se lanza una excepción de tipo
     * {@link CustomerNotFoundException}.
     * 
     * @param id
     * @return Objeto GetAddress con los datos del cliente encontrado.
     * @throws CustomerNotFoundException Si no se encuentra un cliente con el
     *                                   identificador proporcionado.
     */
    GetAddress findByIdAddress(Long id);

    /**
     * Guarda un nuevo cliente en el sistema.
     *
     * @param saveCustomer Objeto con los datos necesarios para crear un nuevo
     *                     cliente.
     * @return Objeto GetCustomer con los datos del cliente recién creado.
     */

    GetCustomer save(SaveCustomer saveCustomer);

    /**
     * Actualiza los datos de un cliente existente identificado por su ID.
     *
     * Si el cliente no es encontrado, se lanza una excepción de tipo
     * {@link CustomerNotFoundException}.
     *
     * @param updateCustomer Objeto con los datos actualizados del cliente.
     * @param id             Identificador del cliente que se desea actualizar.
     * @return Objeto GetCustomer con los datos actualizados del cliente.
     * @throws CustomerNotFoundException Si no se encuentra un cliente con el
     *                                   identificador proporcionado.
     */
    GetCustomer updateById(UpdateCustomer updateCustomer, Long id);

    /**
     * Actualiza la dirección (Address) asociada a un cliente específico.
     *
     * Si el cliente no es encontrado, se lanza una excepción de tipo
     * {@link CustomerNotFoundException}.
     *
     * @param updateAddress Objeto con los nuevos datos de la dirección.
     * @param id            Identificador del cliente cuya dirección se desea
     *                      actualizar.
     * @return Objeto GetAddress con los datos de la dirección actualizada.
     * @throws CustomerNotFoundException Si no se encuentra un cliente con el
     *                                   identificador proporcionado.
     */

    GetAddress upadateAddressById(UpdateAddress updateAddress, Long id);

    /**
     * Elimina un cliente del sistema utilizando su identificador único.
     *
     * Si el cliente no es encontrado, se lanza una excepción de tipo
     * {@link CustomerNotFoundException}.
     *
     * @param id Identificador del cliente a eliminar.
     * @return true si el cliente fue eliminado correctamente, false en caso
     *         contrario.
     * @throws CustomerNotFoundException Si no se encuentra un cliente con el
     *                                   identificador proporcionado.
     */

    boolean deleteById(Long id);

}
