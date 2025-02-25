package com.katabank.repositories;

import com.katabank.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for the BankAccount entity.
 */
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}