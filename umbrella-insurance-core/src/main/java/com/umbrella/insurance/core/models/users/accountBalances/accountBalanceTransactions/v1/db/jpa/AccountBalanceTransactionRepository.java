package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountBalanceTransactionRepository extends JpaRepository<AccountBalanceTransaction, Long> {
    Optional<AccountBalanceTransaction> findByAccountBalanceTransactionName(
            String accountBalanceTransactionName);
    void deleteAccountBalanceTransactionByAccountBalanceTransactionName(String accountBalanceTransactionName);
}
