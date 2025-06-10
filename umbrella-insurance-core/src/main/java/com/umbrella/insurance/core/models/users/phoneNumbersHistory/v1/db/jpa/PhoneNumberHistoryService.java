package com.umbrella.insurance.core.models.users.phoneNumbersHistory.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PhoneNumberHistory;

import java.util.List;
import java.util.Optional;

public interface PhoneNumberHistoryService {
    PhoneNumberHistory savePhoneNumberHistory(PhoneNumberHistory phoneNumberHistory);
    List<PhoneNumberHistory> getPhoneNumberHistory();
    PhoneNumberHistory updatePhoneNumberHistory(PhoneNumberHistory phoneNumberHistory);
    void deletePhoneNumberHistory(Long phoneNumberHistoryId);
    Optional<PhoneNumberHistory> getPhoneNumberHistoryByUserId(Long userId);
    void deletePhoneNumberHistoryByUserId(Long userId);
    Optional<PhoneNumberHistory> getPhoneNumberHistoryByPhoneNumberHistoryId(Long phoneNumberHistoryId);
}
