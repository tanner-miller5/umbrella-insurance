package com.umbrella.insurance.core.models.users.phoneNumbersHistory.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PhoneNumberHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PhoneNumberHistoryServiceImpl implements PhoneNumberHistoryService {
    @Autowired
    PhoneNumberHistoryRepository phoneNumberHistoryRepository;

    @Override
    public PhoneNumberHistory savePhoneNumberHistory(PhoneNumberHistory phoneNumberHistory) {
        return phoneNumberHistoryRepository.save(phoneNumberHistory);
    }

    @Override
    public List<PhoneNumberHistory> getPhoneNumberHistory() {
        return phoneNumberHistoryRepository.findAll();
    }

    @Override
    public PhoneNumberHistory updatePhoneNumberHistory(PhoneNumberHistory phoneNumberHistory) {
        return phoneNumberHistoryRepository.save(phoneNumberHistory);
    }

    @Override
    public void deletePhoneNumberHistory(Long phoneNumberHistoryId) {
        phoneNumberHistoryRepository.deleteById(phoneNumberHistoryId);
    }

    @Override
    public Optional<PhoneNumberHistory> getPhoneNumberHistoryByUserId(Long userId) {
        return phoneNumberHistoryRepository.getPhoneNumberHistoryByUserId(userId);
    }

    @Override
    public void deletePhoneNumberHistoryByUserId(Long userId) {
        phoneNumberHistoryRepository.deletePhoneNumberHistoryByUserId(userId);
    }

    @Override
    public Optional<PhoneNumberHistory> getPhoneNumberHistoryByPhoneNumberHistoryId(Long phoneNumberHistoryId) {
        return phoneNumberHistoryRepository.findById(phoneNumberHistoryId);
    }
}
