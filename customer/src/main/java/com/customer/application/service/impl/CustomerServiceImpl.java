package com.customer.application.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commons.dto.request.SaveCustomer;
import com.commons.dto.response.GetAddress;
import com.commons.dto.response.GetCustomerDetail;
import com.customer.application.dto.request.UpdateAddress;
import com.customer.application.dto.request.UpdateCustomer;
import com.customer.application.dto.response.GetCustomer;
import com.customer.application.exceptions.CustomerNotFoundException;
import com.customer.application.mapper.CustomerMapper;
import com.customer.application.service.CustomerService;
import com.customer.domain.persistence.Customer;
import com.customer.domain.repository.CustomerRepository;

/**
 * Implementación del servicio para gestionar las operaciones relacionadas con
 * los clientes.
 * Este servicio incluye operaciones para buscar, crear, actualizar, eliminar y
 * obtener
 * una lista de clientes, además de manejar excepciones como
 * {@link CustomerNotFoundException}.
 */
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * Constructor para inyectar el repositorio de clientes.
     * 
     * @param customerRepository Repositorio de clientes utilizado para las
     *                           operaciones CRUD.
     */

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Obtiene una lista paginada de todos los clientes activos en el sistema.
     * 
     * @param pageable Objeto que contiene información de paginación y ordenamiento.
     * @return Página de clientes representados como objetos {@link GetCustomer}.
     * @throws CustomerNotFoundException Si no se encuentran clientes activos en el
     *                                   sistema.
     */

    @Transactional(readOnly = true)
    @Override
    public Page<GetCustomer> findAll(Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findActiveAll(pageable);
        if (customerPage.isEmpty()) {
            throw new CustomerNotFoundException("No se encontraron clientes");
        }
        return customerPage.map(CustomerMapper::toDtoFromEntity);
    }

    /**
     * Obtiene el detalle completo de un cliente incluyendo información de
     * dirección.
     * Realiza una búsqueda en base de datos con fetch de los datos relacionados
     * necesarios
     * para construir un DTO completo. La operación se ejecuta dentro de una
     * transacción
     * de solo lectura para optimizar el rendimiento.
     * 
     * @param id Identificador único del cliente (requerido)
     * @return {@link GetCustomerDetail} DTO con todos los campos del cliente
     *         incluyendo:
     *         - Datos básicos
     *         - Información de dirección completa
     * @throws CustomerNotFoundException Si no existe ningún cliente con el ID
     *                                   proporcionado
     * @implNote El método:
     *           - Utiliza el mapper {@link CustomerMapper} para conversión a DTO
     */

    @Transactional(readOnly = true)

    @Override
    public GetCustomerDetail findByIdCustomerDetail(String email) {
        return CustomerMapper.toDtoFCustomerDetail(
                customerRepository.findByEmail(email).orElseThrow(() -> new CustomerNotFoundException(email)));
    }

    /**
     * Obtiene la información básica de un cliente verificando su estado activo.
     * Versión optimizada para operaciones frecuentes que solo requieren datos
     * esenciales.
     * Incluye verificación de estado activo del cliente como requisito para la
     * recuperación.
     * 
     * @param id Identificador único del cliente (debe corresponder a un cliente
     *           activo)
     * @return {@link GetCustomer} DTO
     * @throws CustomerNotFoundException Si:
     *                                   - No existe ningún cliente con el ID
     *                                   - El cliente existe pero está marcado como
     *                                   inactivo
     * @implSpec Este método:
     *           - Ejecuta dentro de contexto transaccional de solo lectura
     *           - Reutiliza la entidad base del método {@code findByIdEntity}
     *           - Aplica filtrado por estado activo automáticamente
     */
    @Transactional(readOnly = true)
    @Override
    public GetCustomer findById(Long id) {
        return CustomerMapper.toDtoFromEntity(findByIdEntity(id));
    }

    /**
     * Busca una Dirrecion de un cliente activo por su indentificador unico
     * Este método verifica que el cliente encontrado esté marcado como activo
     * en el sistema. Si el cliente no está activo o no existe, se lanza una
     * excepción {@link CustomerNotFoundException}.
     * 
     * @param id Identificador único del cliente.
     * @return Objeto {@link GetAddress} con los datos de la direccion del cliente
     *         activo encontrado.
     * @throws CustomerNotFoundException Si no se encuentra un cliente activo con el
     *                                   identificador proporcionado.
     */

    @Transactional(readOnly = true)
    @Override
    public GetAddress findByIdAddress(Long id) {
        return CustomerMapper.toDtoFromAddress(findByIdEntity(id));
    }

    /**
     * Guarda un nuevo cliente en el sistema y lo marca como activo.
     * 
     * Este método guarda un nuevo cliente en el sistema, utilizando los datos
     * proporcionados
     * en el objeto {@link SaveCustomer}. Después de guardar al cliente, el cliente
     * se establece
     * como activo por defecto.
     * 
     * @param saveCustomer Objeto con los datos necesarios para crear un nuevo
     *                     cliente.
     * @return Objeto {@link GetCustomerDetail} con los datos del cliente recién
     *         creado y
     *         marcado como activo.
     */

    @Override
    public GetCustomerDetail save(SaveCustomer saveCustomer) {
        return CustomerMapper
                .toDtoFCustomerDetail(customerRepository.save(CustomerMapper.toEntityFromDto(saveCustomer)));
    }

    /**
     * Actualiza los datos de un cliente existente identificado por su ID.
     * 
     * Este método busca un cliente en el sistema utilizando su ID y, si se
     * encuentra,
     * actualiza sus datos con los proporcionados en el objeto
     * {@link UpdateCustomer}.
     * 
     * El cliente que se actualiza permanece activo en el sistema.
     * 
     * @param updateCustomer Objeto con los datos actualizados del cliente.
     * @param id             Identificador del cliente que se desea actualizar.
     * @return Objeto {@link GetCustomer} con los datos actualizados del cliente.
     * @throws CustomerNotFoundException Si no se encuentra un cliente con el
     *                                   identificador proporcionado.
     */
    @Override
    public GetCustomer updateById(UpdateCustomer updateCustomer, Long id) {
        Customer oldCustomer = findByIdEntity(id);
        CustomerMapper.updateCustomerEntityFromDto(updateCustomer, oldCustomer);
        return CustomerMapper.toDtoFromEntity(customerRepository.save(oldCustomer));
    }

    /**
     * Actualiza la dirección de un cliente específico identificado por su ID.
     * 
     * Este método busca un cliente activo en el sistema utilizando su ID y, si se
     * encuentra,
     * actualiza su dirección con los datos proporcionados en el objeto
     * {@link UpdateAddress}.
     * 
     * La dirección es modificada directamente en la entidad del cliente, y los
     * cambios
     * se persisten en la base de datos.
     * 
     * @param updateAddress Objeto con los nuevos datos de la dirección.
     * @param id            Identificador del cliente cuya dirección se desea
     *                      actualizar.
     * @return Objeto {@link GetAddress} con los datos actualizados de la dirección.
     * @throws CustomerNotFoundException Si no se encuentra un cliente con el
     *                                   identificador proporcionado.
     */

    @Override
    public GetAddress upadateAddressById(UpdateAddress updateAddress, Long id) {
        Customer oldCustomer = findByIdEntity(id);
        CustomerMapper.updateAddressFromDto(updateAddress, oldCustomer.getAddress());
        return CustomerMapper.toDtoFromAddress(customerRepository.save(oldCustomer).getAddress());
    }

    /**
     * Elimina un cliente del sistema utilizando su identificador único.
     * Marca al cliente como eliminado y actualiza su estado a inactivo.
     * 
     * Este método no elimina físicamente al cliente de la base de datos, sino que
     * actualiza su estado a inactivo y registra la fecha de eliminación.
     * 
     * @param id Identificador del cliente a eliminar.
     * @return true si el cliente fue eliminado correctamente, false en caso
     *         contrario.
     * @throws CustomerNotFoundException Si no se encuentra un cliente con el
     *                                   identificador proporcionado.
     */

    @Override
    public boolean deleteById(Long id) {
        Customer deleteCustomer = findByIdEntity(id);
        deleteCustomer.setDeletedAt(LocalDateTime.now());
        deleteCustomer.setIsActive(false);
        return true;
    }

    /**
     * Busca un cliente por su identificador único.
     * 
     * @param id Identificador del cliente.
     * @return Objeto {@link Customer} con los datos del cliente encontrado.
     * @throws CustomerNotFoundException Si no se encuentra un cliente con el
     *                                   identificador proporcionado.
     */
    private Customer findByIdEntity(Long id) {
        return customerRepository.findActiveById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.valueOf(id)));
    }

}
