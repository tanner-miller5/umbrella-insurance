package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceTransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountBalanceTransactionTypeServiceImpl implements AccountBalanceTransactionTypeService {

    @Autowired
    AccountBalanceTransactionTypeRepository accountBalanceTransactionTypeRepository;

    @Override
    public AccountBalanceTransactionType saveAccountBalanceTransactionType(AccountBalanceTransactionType accountBalanceTransactionType) {
        return accountBalanceTransactionTypeRepository.save(accountBalanceTransactionType);
    }

    @Override
    public List<AccountBalanceTransactionType> getAccountBalanceTransactionTypes() {
        return accountBalanceTransactionTypeRepository.findAll();
    }

    @Override
    public AccountBalanceTransactionType updateAccountBalanceTransactionType(AccountBalanceTransactionType accountBalanceTransactionType) {
        return accountBalanceTransactionTypeRepository.save(accountBalanceTransactionType);
    }

    @Override
    public void deleteAccountBalanceTransactionType(Long accountBalanceTransactionTypeId) {
        accountBalanceTransactionTypeRepository.deleteById(accountBalanceTransactionTypeId);
    }

    @Override
    public Optional<AccountBalanceTransactionType> getAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(
            String accountBalanceTransactionTypeName) {
        return accountBalanceTransactionTypeRepository.findByAccountBalanceTransactionTypeName(accountBalanceTransactionTypeName);
    }

    @Override
    public Optional<AccountBalanceTransactionType> getAccountBalanceTransactionTypeById(Long accountBalanceTransactionTypeId) {
        return accountBalanceTransactionTypeRepository.findById(accountBalanceTransactionTypeId);
    }

    @Override
    public void deleteAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(String accountBalanceTransactionTypeName) {
        accountBalanceTransactionTypeRepository.deleteAccountBalanceTransactionTypeByAccountBalanceTransactionTypeName(
                accountBalanceTransactionTypeName);
    }
}
