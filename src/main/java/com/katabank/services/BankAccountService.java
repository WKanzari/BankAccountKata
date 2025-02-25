package com.katabank.services;

import com.katabank.entities.BankAccount;
import com.katabank.model.Statement;
import com.katabank.repositories.BankAccountRepository;
import com.katabank.utils.Constants;
import com.katabank.exceptions.InsufficientBalanceException;
import com.katabank.exceptions.InvalidAmountException;
import com.katabank.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing bank accounts.
 * This class provides methods for creating an account, performing credit and debit operations,
 * checking the balance, and printing account statements.
 */
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private List<Statement> statements = new ArrayList<>();

    /**
     * Constructor for BankAccountService.
     *
     * @param bankAccountRepository the repository used to interact with the BankAccount data store
     */
    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Creates a new bank account and saves it in the repository.
     *
     * @return the newly created BankAccount
     */
    public BankAccount createAccount() {
        return bankAccountRepository.save(new BankAccount());
    }

    /**
     * Performs a credit operation (deposit) on the specified account.
     * This method checks if the account exists and whether the amount is valid (positive).
     * The deposit is recorded in the statement of the account.
     *
     * @param accountId the ID of the account to credit
     * @param amount    the amount to be credited (deposit)
     * @throws AccountNotFoundException if the account with the specified ID is not found
     * @throws InvalidAmountException  if the amount to deposit is not positive
     */
    public void credit(Long accountId, int amount) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(Constants.ACCOUNT_NOT_FOUND_MESSAGE));

        account.credit(amount);
        bankAccountRepository.save(account);
        statements.add(new Statement(Constants.DEPOSIT, amount, account.getBalance()));
    }

    /**
     * Performs a debit operation (withdrawal) from the specified account.
     * This method checks if the account exists, whether the amount is valid (positive),
     * and whether the account has sufficient funds.
     * The withdrawal is recorded in the statement of the account.
     *
     * @param accountId the ID of the account to debit
     * @param amount    the amount to be debited (withdrawn)
     * @throws AccountNotFoundException    if the account with the specified ID is not found
     * @throws InsufficientBalanceException if the account has insufficient funds for the withdrawal
     * @throws InvalidAmountException      if the amount to withdraw is not positive
     */
    public void debit(Long accountId, int amount) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(Constants.ACCOUNT_NOT_FOUND_MESSAGE));

        account.debit(amount);
        bankAccountRepository.save(account);
        statements.add(new Statement(Constants.WITHDRAWAL, amount, account.getBalance()));
    }

    /**
     * Retrieves the current balance of the specified account.
     *
     * @param accountId the ID of the account whose balance is to be retrieved
     * @return the balance of the account
     * @throws AccountNotFoundException if the account with the specified ID is not found
     */
    public int getBalance(Long accountId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(Constants.ACCOUNT_NOT_FOUND_MESSAGE));
        return account.getBalance();
    }

    /**
     * Prints the statement for the specified account.
     * The statement includes all transactions (deposits and withdrawals) recorded for the account.
     *
     * @param accountId the ID of the account for which the statement is to be printed
     * @return a list of all statements (transactions) for the account
     * @throws AccountNotFoundException if the account with the specified ID is not found
     */
    public List<Statement> printStatement(Long accountId) {
        bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(Constants.ACCOUNT_NOT_FOUND_MESSAGE));
        return new ArrayList<>(statements);
    }
}
