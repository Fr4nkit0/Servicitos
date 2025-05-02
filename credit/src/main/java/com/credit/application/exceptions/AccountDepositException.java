package com.credit.application.exceptions;

public class AccountDepositException extends RuntimeException {
    public AccountDepositException(String message) {
        super(message);
    }
}