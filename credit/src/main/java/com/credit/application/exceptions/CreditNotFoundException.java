package com.credit.application.exceptions;

// Excepción personalizada para cuando no se encuentra un crédito
public class CreditNotFoundException extends RuntimeException {
    public CreditNotFoundException(String message) {
        super(message);
    }

    public CreditNotFoundException(Long creditId) {
        super("No se encontró el crédito con id: " + creditId);
    }
}