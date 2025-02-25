package com.katabank.exceptions;

/**
 * Custom exception thrown when an account is not found in the system.
 * This exception is used when an operation (e.g., credit, debit) is attempted
 * on a non-existing account.
 */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}