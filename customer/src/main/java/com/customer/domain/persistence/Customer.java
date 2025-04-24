package com.customer.domain.persistence;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Representa un cliente del sistema.
 * Incluye información personal básica, dirección embebida y fechas de
 * auditoría.
 */

public class Customer {
    /**
     * Identificador único del cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nombre del cliente.
     */

    @Column(name = "name", nullable = false, length = 100)
    private String name;
    /**
     * Apellido del cliente.
     */

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastname;
    /**
     * Correo electrónico del cliente, único en el sistema.
     */

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    /**
     * Número de teléfono móvil del cliente.
     */

    @Column(name = "mobile", length = 20)
    private String mobile;
    /**
     * Dirección embebida del cliente.
     */

    @Embedded
    private Address address;
    /**
     * Indica si el cliente está activo en el sistema.
     */

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    /**
     * Fecha de creación del registro.
     */

    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    /**
     * Fecha de última actualización del registro.
     */
    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updatedAt;
    /**
     * Fecha de eliminación lógica (soft delete).
     */

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

}
