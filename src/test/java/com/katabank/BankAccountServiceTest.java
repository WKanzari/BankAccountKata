package com.katabank;

import static org.junit.jupiter.api.Assertions.*;

import com.katabank.entities.BankAccount;
import com.katabank.exceptions.InsufficientBalanceException;
import com.katabank.exceptions.InvalidAmountException;
import com.katabank.model.Statement;
import com.katabank.services.BankAccountService;
import com.katabank.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    void testCreditNegativeAmount() {
        InvalidAmountException exception = assertThrows(InvalidAmountException.class, () -> {
            bankAccountService.credit(1L, -100);
        });
        assertEquals(Constants.INVALID_AMOUNT_MESSAGE, exception.getMessage());
    }

    @Test
    public void testDebit() {
        BankAccount account = bankAccountService.createAccount();
        bankAccountService.credit(account.getId(), 100);
        bankAccountService.debit(account.getId(), 50);
        assertEquals(50, bankAccountService.getBalance(account.getId()));
    }

    @Test
    void testDebitInsufficientBalance() {
        InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
            bankAccountService.debit(1L, 1000);
        });
        assertEquals(Constants.INSUFFICIENT_BALANCE_MESSAGE, exception.getMessage());
    }

    @Test
    public void testPrintStatement() {
        bankAccountService.credit(account.getId(), 100);
        bankAccountService.debit(account.getId(), 50);

        List<Statement> statements = bankAccountService.printStatement(account.getId());

        assertEquals(2, statements.size());
        assertEquals(Constants.DEPOSIT, statements.get(0).getType());
        assertEquals(100, statements.get(0).getAmount());
        assertEquals(50, statements.get(1).getAmount());
        assertEquals(Constants.WITHDRAWAL, statements.get(1).getType());
    }
}