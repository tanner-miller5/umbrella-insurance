package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceType;

import java.util.List;
import java.util.Optional;

public interface AccountBalanceTypeService {
    AccountBalanceType saveAccountBalanceType(AccountBalanceType accountBalanceType);
    List<AccountBalanceType> getAccountBalanceTypes();
    AccountBalanceType updateAccountBalanceType(AccountBalanceType accountBalanceType);
    void deleteAccountBalanceType(Long accountBalanceTypeId);
    Optional<AccountBalanceType> findAccountBalanceTypeByAccountBalanceTypeName(
            String accountBalanceTypeName);
    Optional<AccountBalanceType> findAccountBalanceTypeById(Long accountBalanceTypeId);
    void deleteAccountBalanceTypeByAccountBalanceTypeName(String accountBalanceTypeName);
}
