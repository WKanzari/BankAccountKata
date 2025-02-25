package com.katabank.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class BankAccount {

    @Id
    @GeneratedValue
    private Long id;
    private int balance;

    public BankAccount() {
        this.balance = 0;
    }

    public void credit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("the deposit amount must be positive");
        }
        balance += amount;
    }
}