package com.account.domain.util;

/**
 * Enum que representa los diferentes tipos de cuenta bancaria ofrecidos por la
 * entidad financiera.
 * 
 * - AHORRO: Cuenta que genera intereses sobre el saldo disponible. Usada
 * comúnmente para ahorrar.
 * - CORRIENTE: Cuenta de uso frecuente para transacciones diarias. Puede
 * incluir sobregiros.
 * - PLAZO_FIJO: Cuenta que bloquea un monto durante un período determinado a
 * cambio de intereses más altos.
 */
public enum AccountType {
    AHORRO,
    CORRIENTE,
    PLAZO_FIJO

}
