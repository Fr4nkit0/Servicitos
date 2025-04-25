package com.customer.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.commons.dto.request.SaveCustomer;
import com.commons.dto.response.GetAddress;
import com.commons.dto.response.GetCustomerDetail;
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
     * Obtiene el perfil completo de un cliente incluyendo su dirección.
     * 
     * Recupera todos los datos del cliente junto con la información detallada de su
     * dirección.
     * Si el cliente no es encontrado, se lanza una excepción de tipo
     * {@link CustomerNotFoundException}.
     *
     * @param email Identificador del cliente
     * @return Objeto {@link GetCustomerDetail} con la información completa del
     *         cliente,
     *         incluyendo todos sus campos y datos de dirección
     * @throws CustomerNotFoundException Si no existe un cliente con el ID
     *                                   proporcionado
     * @apiNote Use este método cuando necesite toda la información del cliente
     *          incluyendo
     *          detalles de localización
     */

    GetCustomerDetail findByIdCustomerDetail(String email);

    /**
     * Obtiene la información básica de un cliente.
     * 
     * Recupera los datos principales del cliente sin incluir información de
     * dirección.
     * Si el cliente no es encontrado, se lanza una excepción de tipo
     * {@link CustomerNotFoundException}.
     *
     * @param id Identificador único del cliente
     * @return Objeto {@link GetCustomer} con los datos esenciales del cliente,
     *         excluyendo información de dirección
     * @throws CustomerNotFoundException Si no existe un cliente con el ID
     *                                   proporcionado
     * @apiNote Use este método para operaciones que solo requieren datos básicos
     *          del cliente
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
