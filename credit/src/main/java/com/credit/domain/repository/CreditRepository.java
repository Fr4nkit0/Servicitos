package com.credit.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.credit.domain.persistence.Credit;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    @Query("SELECT c FROM Credit c WHERE c.isActive = true")
    Page<Credit> findActiveAll(Pageable pageable);

    @Query("SELECT c FROM Credit c WHERE c.isActive = true AND c.id = ?1")
    Optional<Credit> findActiveById(Long id);
}
