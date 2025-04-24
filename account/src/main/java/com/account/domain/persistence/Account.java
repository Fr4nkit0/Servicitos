package com.account.domain.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.account.domain.util.AccountStatus;
import com.account.domain.util.AccountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa una cuenta asociada a un cliente.
 * Puede ser de tipo ahorro, corriente, etc., y está relacionada con un cliente
 * por su ID.
 */
@Entity
@Table(name = "accounts")
public class Account {

    /**
     * Identificador único de la cuenta.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Número de cuenta bancario único.
     */
    @Column(name = "account_number", nullable = false, unique = true, length = 30)
    private String accountNumber;

    /**
     * Tipo de cuenta: puede ser 'AHORRO', 'CORRIENTE', 'PLAZO_FIJO', etc.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 20)
    private AccountType accountType;

    /**
     * Estado de la cuenta: puede ser 'ACTIVA', 'SUSPENDIDA', 'CERRADA'.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AccountStatus status;

    /**
     * Saldo total de la cuenta.
     */
    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;
    /**
     * Marca temporal de creación automática de la entidad.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Marca temporal de última actualización de la entidad.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * ID del cliente asociado a esta cuenta.
     */
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
}
