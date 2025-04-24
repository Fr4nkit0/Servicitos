package com.account.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.account.domain.persistence.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.isActive = true AND a.id = ?1")
    Optional<Account> findById(Long id);

    @Query("SELECT a FROM Account a WHERE a.isActive = true AND a.accountNumber = ?1")
    boolean existsByAccountNumber(String accountNumber);
}
