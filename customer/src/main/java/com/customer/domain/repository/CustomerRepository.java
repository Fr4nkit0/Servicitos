package com.customer.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.customer.domain.persistence.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM customers c WHERE c.is_active = true", nativeQuery = true)
    Page<Customer> findActiveAll(Pageable pageable);

    @Query(value = "SELECT * FROM customers c WHERE c.is_active = true AND c.id = ?1", nativeQuery = true)
    Optional<Customer> findActiveById(Long id);

    @Query(value = "SELECT * FROM customers c WHERE c.is_active = true AND c.email = ?1", nativeQuery = true)
    Optional<Customer> findByEmail(String email);

}
