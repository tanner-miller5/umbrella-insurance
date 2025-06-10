package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceTransactionStatus;
import java.util.List;
import java.util.Optional;

public interface AccountBalanceTransactionStatusService {
    AccountBalanceTransactionStatus saveAccountBalanceTransactionStatus(AccountBalanceTransactionStatus accountBalanceTransactionStatus);
    List<AccountBalanceTransactionStatus> getAccountBalanceTransactionStatuses();
    AccountBalanceTransactionStatus updateAccountBalanceTransactionStatus(AccountBalanceTransactionStatus accountBalanceTransactionStatus);
    void deleteAccountBalanceTransactionStatus(Long accountBalanceTransactionStatusId);
    Optional<AccountBalanceTransactionStatus> getAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(
            String accountBalanceTransactionStatusName);
    Optional<AccountBalanceTransactionStatus> getAccountBalanceTransactionStatusByAccountBalanceTransactionStatusId(
            Long accountBalanceTransactionStatusId);
    void deleteAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(String accountBalanceTransactionStatusName);
}
