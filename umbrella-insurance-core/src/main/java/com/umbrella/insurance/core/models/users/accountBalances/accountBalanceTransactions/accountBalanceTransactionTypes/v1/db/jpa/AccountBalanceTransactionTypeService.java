package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceTransactionType;

import java.util.List;
import java.util.Optional;

public interface AccountBalanceTransactionTypeService {
    AccountBalanceTransactionType saveAccountBalanceTransactionType(AccountBalanceTransactionType accountBalanceTransactionType);
    List<AccountBalanceTransactionType> getAccountBalanceTransactionTypes();
    AccountBalanceTransactionType updateAccountBalanceTransactionType(AccountBalanceTransactionType accountBalanceTransactionType);
    void deleteAccountBalanceTransactionType(Long accountBalanceTransactionTypeId);
    Optional<AccountBalanceTransactionType> getAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(
            String accountBalanceTransactionTypeName);
    Optional<AccountBalanceTransactionType> getAccountBalanceTransactionTypeById(Long accountBalanceTransactionTypeId);
    void deleteAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(String accountBalanceTransactionTypeName);
}
