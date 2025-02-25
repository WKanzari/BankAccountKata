package com.katabank;

import static org.junit.jupiter.api.Assertions.*;

import com.katabank.entities.BankAccount;
import com.katabank.services.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BankAccountServiceTest {

    @Autowired
    private BankAccountService bankAccountService;

    private BankAccount account;

    @BeforeEach
    public void setUp() {
        account = bankAccountService.createAccount();
    }

    @Test
    public void testCredit() {
        bankAccountService.credit(account.getId(), 100);
        assertEquals(100, bankAccountService.getBalance(account.getId()));
    }

    @Test
    public void testCreditNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> bankAccountService.credit(account.getId(), -50));
    }

    @Test
    public void testDebit() {
        BankAccount account = bankAccountService.createAccount();
        bankAccountService.credit(account.getId(), 100);
        bankAccountService.debit(account.getId(), 50);
        assertEquals(50, bankAccountService.getBalance(account.getId()));
    }

    @Test
    public void testDebitInsufficientBalance() {
        BankAccount account = bankAccountService.createAccount();
        bankAccountService.credit(account.getId(), 100);
        assertThrows(IllegalStateException.class, () -> bankAccountService.debit(account.getId(), 150));
    }
}