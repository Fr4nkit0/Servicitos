package com.account.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.account.domain.persistence.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.isActive = true")
    Page<Account> findActiveAll(Pageable pageable);

    @Query("SELECT a FROM  Account a WHERE a.isActive = true AND a.id = ?1")
    Optional<Account> findActiveById(Long id);

    @Query("SELECT a FROM Account a WHERE a.isActive = true AND a.accountNumber = ?1")
    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("SELECT a FROM Account a WHERE a.isActive = true AND a.accountNumber = ?1")
    boolean existsByAccountNumber(String accountNumber);
}
