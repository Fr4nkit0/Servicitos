package com.customer.application.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.customer.application.dto.request.SaveCustomer;
import com.customer.application.dto.request.UpdateAddress;
import com.customer.application.dto.request.UpdateCustomer;
import com.customer.application.dto.response.GetAddress;
import com.customer.application.dto.response.GetCustomer;
import com.customer.application.exceptions.CustomerNotFoundException;
import com.customer.application.mapper.CustomerMapper;
import com.customer.application.service.CustomerService;
import com.customer.domain.persistence.Customer;
import com.customer.domain.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Page<GetCustomer> findAll(Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        if (customerPage.isEmpty()) {
            throw new CustomerNotFoundException("No se encontraron clientes");
        }
        return customerPage.map(CustomerMapper::toDtoFromEntity);
    }

    @Override
    public GetCustomer findById(Long id) {
        return CustomerMapper.toDtoFromEntity(findByIdEntity(id));
    }

    @Override
    public GetCustomer save(SaveCustomer saveCustomer) {
        return CustomerMapper.toDtoFromEntity(customerRepository.save(CustomerMapper.toEntityFromDto(saveCustomer)));
    }

    @Override
    public GetCustomer updateById(UpdateCustomer updateCustomer, Long id) {
        Customer oldCustomer = findByIdEntity(id);
        CustomerMapper.updateCustomerEntityFromDto(updateCustomer, oldCustomer);
        return CustomerMapper.toDtoFromEntity(customerRepository.save(oldCustomer));
    }

    @Override
    public GetAddress upadateAddressById(UpdateAddress updateAddress, Long id) {
        Customer oldCustomer = findByIdEntity(id);
        CustomerMapper.updateAddressFromDto(updateAddress, oldCustomer.getAddress());
        return CustomerMapper.toDtoFromAddress(customerRepository.save(oldCustomer).getAddress());
    }

    @Override
    public boolean deleteById(Long id) {
        Customer deleteCustomer = findByIdEntity(id);
        deleteCustomer.setDeletedAt(LocalDateTime.now());
        deleteCustomer.setIsActive(false);
        return true;
    }

    private Customer findByIdEntity(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(String.valueOf(id)));
    }

}
