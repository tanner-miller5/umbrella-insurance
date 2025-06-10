package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountBalanceTypeRepository extends JpaRepository<AccountBalanceType, Long> {
    Optional<AccountBalanceType> findAccountBalanceTypeByAccountBalanceTypeName(
            String accountBalanceTypeName);
    void deleteAccountBalanceTypeByAccountBalanceTypeName(String accountBalanceTypeName);
}
