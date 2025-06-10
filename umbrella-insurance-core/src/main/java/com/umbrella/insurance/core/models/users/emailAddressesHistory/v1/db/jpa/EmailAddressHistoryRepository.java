package com.umbrella.insurance.core.models.users.emailAddressesHistory.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.EmailAddressHistory;

import java.util.Optional;

@Repository
public interface EmailAddressHistoryRepository extends JpaRepository<EmailAddressHistory, Long> {
    Optional<EmailAddressHistory> getEmailAddressHistoryByUserId(Long userId);
    void deleteEmailAddressHistoryByUserId(Long userId);
}
