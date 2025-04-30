package com.account.application.exceptions;

public class CustomerCreationException extends AccountServiceException {
    public CustomerCreationException(String message) {
        super(message);
    }
}
