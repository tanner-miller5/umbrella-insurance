package com.umbrella.insurance.core.models.users.emailAddressesHistory.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.umbrella.insurance.core.models.entities.EmailAddressHistory;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmailAddressHistoryServiceImpl implements EmailAddressHistoryService {
    @Autowired
    EmailAddressHistoryRepository emailAddressHistoryRepository;

    @Override
    public EmailAddressHistory saveEmailAddressHistory(EmailAddressHistory emailAddressHistory) {
        return emailAddressHistoryRepository.save(emailAddressHistory);
    }

    @Override
    public List<EmailAddressHistory> getEmailAddressHistory() {
        return emailAddressHistoryRepository.findAll();
    }

    @Override
    public EmailAddressHistory updateEmailAddressHistory(EmailAddressHistory emailAddressHistory) {
        return emailAddressHistoryRepository.save(emailAddressHistory);
    }

    @Override
    public void deleteEmailAddressHistory(Long emailAddressHistoryId) {
        emailAddressHistoryRepository.deleteById(emailAddressHistoryId);
    }

    @Override
    public Optional<EmailAddressHistory> getEmailAddressHistoryByUserId(Long userId) {
        return emailAddressHistoryRepository.getEmailAddressHistoryByUserId(userId);
    }

    @Override
    public Optional<EmailAddressHistory> getEmailAddressHistoryByEmailAddressHistoryId(Long emailAddressHistoryId) {
        return emailAddressHistoryRepository.findById(emailAddressHistoryId);
    }

    @Override
    public void deleteEmailAddressHistoryByUserId(Long userId) {
        emailAddressHistoryRepository.deleteEmailAddressHistoryByUserId(userId);
    }
}
