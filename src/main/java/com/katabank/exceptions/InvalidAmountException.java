package com.katabank.exceptions;

/**
 * Custom exception thrown when an invalid amount is provided for a transaction.
 * This exception is used when a non-positive amount (e.g., zero or negative) is
 * passed for a deposit or withdrawal operation, which is not allowed.
 */
public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super(message);
    }
}