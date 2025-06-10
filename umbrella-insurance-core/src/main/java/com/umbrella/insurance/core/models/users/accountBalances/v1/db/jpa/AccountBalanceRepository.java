package com.umbrella.insurance.core.models.users.accountBalances.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountBalanceRepository  extends JpaRepository<AccountBalance, Long> {
    Optional<AccountBalance> findAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(
            Long userId, Long accountBalanceTypeId, Long unitId);
    List<AccountBalance> findAccountBalancesByUserId(Long userId);
    void deleteByUserId(Long userId);
    void deleteAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(
            Long userId, Long accountBalanceTypeId, Long unitId
    );
}
