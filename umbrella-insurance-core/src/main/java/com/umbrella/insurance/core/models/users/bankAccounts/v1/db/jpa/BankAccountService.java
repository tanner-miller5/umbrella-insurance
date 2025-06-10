package com.umbrella.insurance.core.models.users.bankAccounts.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.BankAccount;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    BankAccount saveBankAccount(BankAccount bankAccount);
    List<BankAccount> getBankAccounts();
    BankAccount updateBankAccount(BankAccount bankAccount);
    void deleteBankAccount(Long bankAccountId);
    Optional<BankAccount> getBankAccountByUserId(Long userId);
    void deleteBankAccountByUserId(Long userId);
    Optional<BankAccount> getBankAccountById(Long id);
}
