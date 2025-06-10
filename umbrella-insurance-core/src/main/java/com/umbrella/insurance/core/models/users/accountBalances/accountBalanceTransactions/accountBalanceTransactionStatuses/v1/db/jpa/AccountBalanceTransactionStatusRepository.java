package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceTransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountBalanceTransactionStatusRepository extends JpaRepository<AccountBalanceTransactionStatus, Long> {
    Optional<AccountBalanceTransactionStatus> findByAccountBalanceTransactionStatusName(
            String accountBalanceTransactionStatusName);
    void deleteByAccountBalanceTransactionStatusName(String accountBalanceTransactionStatusName);
}
