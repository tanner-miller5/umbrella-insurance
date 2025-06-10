package com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTransactions.accountBalanceTransactionStatuses.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.AccountBalanceTransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountBalanceTransactionStatusServiceImpl implements AccountBalanceTransactionStatusService {
    @Autowired
    AccountBalanceTransactionStatusRepository accountBalanceTransactionStatusRepository;

    @Override
    public AccountBalanceTransactionStatus saveAccountBalanceTransactionStatus(AccountBalanceTransactionStatus accountBalanceTransactionStatus) {
        return accountBalanceTransactionStatusRepository.save(accountBalanceTransactionStatus);
    }

    @Override
    public List<AccountBalanceTransactionStatus> getAccountBalanceTransactionStatuses() {
        return accountBalanceTransactionStatusRepository.findAll();
    }

    @Override
    public AccountBalanceTransactionStatus updateAccountBalanceTransactionStatus(AccountBalanceTransactionStatus accountBalanceTransactionStatus) {
        return accountBalanceTransactionStatusRepository.save(accountBalanceTransactionStatus);
    }

    @Override
    public void deleteAccountBalanceTransactionStatus(Long accountBalanceTransactionStatusId) {
        accountBalanceTransactionStatusRepository.deleteById(accountBalanceTransactionStatusId);
    }

    @Override
    public Optional<AccountBalanceTransactionStatus> getAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(
            String accountBalanceTransactionStatusName) {
        return accountBalanceTransactionStatusRepository.findByAccountBalanceTransactionStatusName(
                accountBalanceTransactionStatusName);
    }

    @Override
    public Optional<AccountBalanceTransactionStatus> getAccountBalanceTransactionStatusByAccountBalanceTransactionStatusId(
            Long accountBalanceTransactionStatusId
    ) {
        return accountBalanceTransactionStatusRepository.findById(accountBalanceTransactionStatusId);
    }

    @Override
    public void deleteAccountBalanceTransactionStatusByAccountBalanceTransactionStatusName(String accountBalanceTransactionStatusName) {
        accountBalanceTransactionStatusRepository.deleteByAccountBalanceTransactionStatusName(
                accountBalanceTransactionStatusName);
    }
}
