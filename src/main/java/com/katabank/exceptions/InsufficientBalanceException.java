package com.katabank.exceptions;

/**
 * Custom exception thrown when an account has insufficient balance
 * to perform a withdrawal. This exception is used when an operation
 * attempts to debit more money than is available in the account.
 */
public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}