package com.umbrella.insurance.core.models.users.phoneNumbersHistory.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PhoneNumberHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneNumberHistoryRepository extends JpaRepository<PhoneNumberHistory, Long> {
    Optional<PhoneNumberHistory> getPhoneNumberHistoryByUserId(Long userId);
    void deletePhoneNumberHistoryByUserId(Long userId);
}
