package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountBalanceTransactionTypeRepository extends JpaRepository<AccountBalanceTransactionType, Long> {
    Optional<AccountBalanceTransactionType> findByAccountBalanceTransactionTypeName(
            String accountBalanceTransactionTypeName);
    void deleteAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(String accountBalanceTransactionTypeName);
}
