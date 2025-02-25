package com.katabank.entities;

import com.katabank.exceptions.InsufficientBalanceException;
import com.katabank.exceptions.InvalidAmountException;
import com.katabank.utils.Constants;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity class representing a bank account.
 * This class contains information about the account's balance and provides methods
 * to perform operations such as credit (deposit) and debit (withdrawal).
 */
@Entity
@Data
public class BankAccount {

    @Id
    @GeneratedValue
    private Long id;
    private int balance;

    /**
     * Default constructor for the BankAccount entity.
     * Initializes the balance to 0.
     */
    public BankAccount() {
        this.balance = 0;
    }

    /**
     * Method to credit (deposit) a specified amount to the bank account.
     *
     * @param amount the amount to be deposited. Must be positive.
     * @throws InvalidAmountException if the provided amount is not positive.
     */
    public void credit(int amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(Constants.INVALID_AMOUNT_MESSAGE);  // Throw exception if amount is invalid
        }
        balance += amount;
    }

    /**
     * Method to debit (withdraw) a specified amount from the bank account.
     *
     * @param amount the amount to be withdrawn. Must be positive and not exceed the balance.
     * @throws InvalidAmountException if the provided amount is not positive.
     * @throws InsufficientBalanceException if there are insufficient funds in the account.
     */
    public void debit(int amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(Constants.INVALID_AMOUNT_MESSAGE);  // Throw exception if amount is invalid
        }
        if (balance < amount) {
            throw new InsufficientBalanceException(Constants.INSUFFICIENT_BALANCE_MESSAGE);  // Throw exception if insufficient balance
        }
        balance -= amount;
    }
}
