package com.umbrella.insurance.core.models.users.bankAccounts.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount saveBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public List<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Override
    public BankAccount updateBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public void deleteBankAccount(Long bankAccountId) {
        bankAccountRepository.deleteById(bankAccountId);
    }

    @Override
    public Optional<BankAccount> getBankAccountByUserId(Long userId) {
        return bankAccountRepository.findBankAccountByUserId(userId);
    }

    @Override
    public void deleteBankAccountByUserId(Long userId) {
        bankAccountRepository.deleteBankAccountByUserId(userId);
    }

    @Override
    public Optional<BankAccount> getBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }
}
