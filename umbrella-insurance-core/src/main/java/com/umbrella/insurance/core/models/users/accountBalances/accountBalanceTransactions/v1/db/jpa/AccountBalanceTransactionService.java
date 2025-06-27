package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceTransaction;

import java.util.List;
import java.util.Optional;

public interface AccountBalanceTransactionService {
    AccountBalanceTransaction saveAccountBalanceTransaction(AccountBalanceTransaction accountBalanceTransaction);
    List<AccountBalanceTransaction> getAccountBalanceTransactions();
    AccountBalanceTransaction updateAccountBalanceTransaction(AccountBalanceTransaction accountBalanceTransaction);
    void deleteAccountBalanceTransaction(Long accountBalanceTransactionId);
    Optional<AccountBalanceTransaction> getAccountBalanceTransactionByAccountBalanceTransactionName(
            String accountBalanceTransactionName);
    Optional<AccountBalanceTransaction> getAccountBalanceTransactionByAccountBalanceTransactionId(
            Long accountBalanceTransactionId);
    void deleteAccountBalanceTransactionByAccountBalanceTransactionName(String accountBalanceTransactionName);
    List<AccountBalanceTransaction> getAccountBalanceTransactionsByUserId(Long userId);
}
