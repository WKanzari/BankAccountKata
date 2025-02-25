package com.katabank.services;

import com.katabank.entities.BankAccount;
import com.katabank.model.Statement;
import com.katabank.repositories.BankAccountRepository;
import com.katabank.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private List<Statement> statements = new ArrayList<>();

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccount createAccount() {
        return bankAccountRepository.save(new BankAccount());
    }

    public void credit(Long accountId, int amount) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.credit(amount);
        bankAccountRepository.save(account);
        statements.add(new Statement(Constants.DEPOSIT, amount, account.getBalance()));
    }

    public void debit(Long accountId, int amount) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.debit(amount);
        bankAccountRepository.save(account);
        statements.add(new Statement(Constants.WITHDRAWAL, amount, account.getBalance()));
    }

    public int getBalance(Long accountId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return account.getBalance();
    }

    public List<Statement> printStatement(Long accountId) {
        bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        return new ArrayList<>(statements);
    }
}