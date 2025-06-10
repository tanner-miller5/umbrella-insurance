package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountBalanceTransactionServiceImpl  implements AccountBalanceTransactionService {
    @Autowired
    AccountBalanceTransactionRepository accountBalanceTransactionRepository;

    @Override
    public AccountBalanceTransaction saveAccountBalanceTransaction(AccountBalanceTransaction accountBalanceTransaction) {
        return accountBalanceTransactionRepository.save(accountBalanceTransaction);
    }

    @Override
    public List<AccountBalanceTransaction> getAccountBalanceTransactions() {
        return accountBalanceTransactionRepository.findAll();
    }

    @Override
    public AccountBalanceTransaction updateAccountBalanceTransaction(AccountBalanceTransaction accountBalanceTransaction) {
        return accountBalanceTransactionRepository.save(accountBalanceTransaction);
    }

    @Override
    public void deleteAccountBalanceTransaction(Long accountBalanceTransactionId) {
        accountBalanceTransactionRepository.deleteById(accountBalanceTransactionId);
    }

    @Override
    public Optional<AccountBalanceTransaction> getAccountBalanceTransactionByAccountBalanceTransactionName(
            String accountBalanceTransactionName) {
        return accountBalanceTransactionRepository.findByAccountBalanceTransactionName(
                accountBalanceTransactionName);
    }

    @Override
    public Optional<AccountBalanceTransaction> getAccountBalanceTransactionByAccountBalanceTransactionId(Long accountBalanceTransactionId) {
        return accountBalanceTransactionRepository.findById(accountBalanceTransactionId);
    }

    @Override
    public void deleteAccountBalanceTransactionByAccountBalanceTransactionName(String accountBalanceTransactionName) {
        accountBalanceTransactionRepository.deleteAccountBalanceTransactionByAccountBalanceTransactionName(
                accountBalanceTransactionName);
    }
}
