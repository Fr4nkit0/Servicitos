package com.account.domain.util;

/**
 * Enum que representa los posibles estados de una cuenta bancaria.
 * 
 * - ACTIVA: La cuenta se encuentra operativa y puede realizar transacciones.
 * - SUSPENDIDA: La cuenta está temporalmente inactiva, por ejemplo, por
 * sospecha de fraude.
 * - CERRADA: La cuenta ha sido cerrada permanentemente y ya no puede usarse.
 */
public enum AccountStatus {

    ACTIVA,
    SUSPENDIDA,
    CERRADA
}
