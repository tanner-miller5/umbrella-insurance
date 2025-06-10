package com.umbrella.insurance.core.models.users.accountBalances.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalance;

import java.util.List;
import java.util.Optional;

public interface AccountBalanceService {
    AccountBalance saveAccountBalance(AccountBalance accountBalance);
    List<AccountBalance> getAccountBalances();
    AccountBalance updateAccountBalance(AccountBalance accountBalance);
    void deleteAccountBalance(Long accountBalanceId);
    Optional<AccountBalance> getAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(
            Long userId, Long accountBalanceTypeId, Long unitId);
    List<AccountBalance> getAccountBalancesByUserId(Long userId);
    void deleteAccountBalanceByUserId(Long userId);
    Optional<AccountBalance> getAccountBalanceByAccountBalanceId(Long accountBalanceId);
    void deleteAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(
            Long userId, Long accountBalanceTypeId, Long unitId);
}
