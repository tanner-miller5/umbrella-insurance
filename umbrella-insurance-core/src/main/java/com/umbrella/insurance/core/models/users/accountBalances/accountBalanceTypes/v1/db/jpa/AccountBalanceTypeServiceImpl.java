package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountBalanceTypeServiceImpl implements AccountBalanceTypeService {
    @Autowired
    AccountBalanceTypeRepository accountBalanceTypeRepository;

    @Override
    public AccountBalanceType saveAccountBalanceType(AccountBalanceType accountBalanceType) {
        return accountBalanceTypeRepository.save(accountBalanceType);
    }

    @Override
    public List<AccountBalanceType> getAccountBalanceTypes() {
        return accountBalanceTypeRepository.findAll();
    }

    @Override
    public AccountBalanceType updateAccountBalanceType(AccountBalanceType accountBalanceType) {
        return accountBalanceTypeRepository.save(accountBalanceType);
    }

    @Override
    public void deleteAccountBalanceType(Long accountBalanceTypeId) {
        accountBalanceTypeRepository.deleteById(accountBalanceTypeId);
    }

    @Override
    public Optional<AccountBalanceType> findAccountBalanceTypeByAccountBalanceTypeName(String accountBalanceTypeName) {
        return accountBalanceTypeRepository.findAccountBalanceTypeByAccountBalanceTypeName(
                accountBalanceTypeName);
    }

    @Override
    public Optional<AccountBalanceType> findAccountBalanceTypeById(Long accountBalanceTypeId) {
        return accountBalanceTypeRepository.findById(accountBalanceTypeId);
    }

    @Override
    public void deleteAccountBalanceTypeByAccountBalanceTypeName(String accountBalanceTypeName) {
        accountBalanceTypeRepository.deleteAccountBalanceTypeByAccountBalanceTypeName(accountBalanceTypeName);
    }
}
