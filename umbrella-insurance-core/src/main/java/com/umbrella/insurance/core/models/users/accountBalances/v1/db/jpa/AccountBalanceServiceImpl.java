package com.umbrella.insurance.core.models.users.accountBalances.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountBalanceServiceImpl implements AccountBalanceService {
    @Autowired
    AccountBalanceRepository accountBalanceRepository;

    @Override
    public AccountBalance saveAccountBalance(AccountBalance accountBalance) {
        return accountBalanceRepository.save(accountBalance);
    }

    @Override
    public List<AccountBalance> getAccountBalances() {
        return accountBalanceRepository.findAll();
    }

    @Override
    public AccountBalance updateAccountBalance(AccountBalance accountBalance) {
        return accountBalanceRepository.save(accountBalance);
    }

    @Override
    public void deleteAccountBalance(Long accountBalanceId) {
        accountBalanceRepository.deleteById(accountBalanceId);
    }

    @Override
    public Optional<AccountBalance> getAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(Long userId, Long accountBalanceTypeId, Long unitId) {
        return accountBalanceRepository.findAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(
                userId, accountBalanceTypeId, unitId
        );
    }

    @Override
    public List<AccountBalance> getAccountBalancesByUserId(Long userId) {
        return accountBalanceRepository.findAccountBalancesByUserId(userId);
    }

    @Override
    public void deleteAccountBalanceByUserId(Long userId) {
        accountBalanceRepository.deleteByUserId(userId);
    }

    @Override
    public Optional<AccountBalance> getAccountBalanceByAccountBalanceId(Long accountBalanceId) {
        return accountBalanceRepository.findById(accountBalanceId);
    }

    @Override
    public void deleteAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(Long userId, Long accountBalanceTypeId, Long unitId) {
        accountBalanceRepository.deleteAccountBalanceByUserIdAndAccountBalanceTypeIdAndUnitId(
                userId, accountBalanceTypeId, unitId);
    }

}
