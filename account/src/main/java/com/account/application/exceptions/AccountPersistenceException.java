package com.account.application.exceptions;

public class AccountPersistenceException extends AccountServiceException {

    public AccountPersistenceException(String message) {
        super(message);
    }

    public AccountPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
