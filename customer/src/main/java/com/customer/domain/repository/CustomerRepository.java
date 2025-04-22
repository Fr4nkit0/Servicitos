package com.customer.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.domain.persistence.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
