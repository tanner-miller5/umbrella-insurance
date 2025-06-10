package com.umbrella.insurance.core.models.users.emailAddressesHistory.v1.db.jpa;

import java.util.List;
import java.util.Optional;

import com.umbrella.insurance.core.models.entities.EmailAddressHistory;

public interface EmailAddressHistoryService {
    EmailAddressHistory saveEmailAddressHistory(EmailAddressHistory emailAddressHistory);
    List<EmailAddressHistory> getEmailAddressHistory();
    EmailAddressHistory updateEmailAddressHistory(EmailAddressHistory emailAddressHistory);
    void deleteEmailAddressHistory(Long emailAddressHistoryId);
    Optional<EmailAddressHistory> getEmailAddressHistoryByUserId(Long userId);
    Optional<EmailAddressHistory> getEmailAddressHistoryByEmailAddressHistoryId(Long emailAddressHistoryId);
    void deleteEmailAddressHistoryByUserId(Long userId);
}
