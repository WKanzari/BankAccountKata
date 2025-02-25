package com.katabank.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Statement {
    
    private String type;
    private int amount;
    private int balance;
    private LocalDateTime date;

    public Statement(String type, int amount, int balance) {
        this.type = type;
        this.amount = amount;
        this.balance = balance;
        this.date = LocalDateTime.now();
    }
}