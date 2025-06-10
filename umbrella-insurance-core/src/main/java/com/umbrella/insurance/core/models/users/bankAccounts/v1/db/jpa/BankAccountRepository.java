package com.umbrella.insurance.core.models.users.bankAccounts.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findBankAccountByUserId(Long userId);
    void deleteBankAccountByUserId(Long userId);
}
