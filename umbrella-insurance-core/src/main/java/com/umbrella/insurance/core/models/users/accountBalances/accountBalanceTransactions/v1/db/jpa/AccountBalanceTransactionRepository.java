package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountBalanceTransactionRepository extends JpaRepository<AccountBalanceTransaction, Long> {
    Optional<AccountBalanceTransaction> findByAccountBalanceTransactionName(
            String accountBalanceTransactionName);
    void deleteAccountBalanceTransactionByAccountBalanceTransactionName(String accountBalanceTransactionName);
    @Query(value = "select abt.* from account_balance_transactions abt " +
            "inner join pending_policies pp on pp.account_balance_escrow_transaction_id = abt.account_balance_transaction_id " +
            "inner join sessions s on s.session_id = pp.session_id " +
            "where s.user_id = :userId"
            , nativeQuery = true)
    List<AccountBalanceTransaction> findByUserId(Long userId);
}
