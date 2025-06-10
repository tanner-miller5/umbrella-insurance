package com.umbrella.insurance.core.models.users.usernamesHistory.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.UsernameHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsernameHistoryServiceImpl implements UsernameHistoryService {

    @Autowired
    UsernameHistoryRepository usernameHistoryRepository;

    @Override
    public UsernameHistory saveUsernameHistory(UsernameHistory usernameHistory) {
        return usernameHistoryRepository.save(usernameHistory);
    }

    @Override
    public List<UsernameHistory> getUsernameHistory() {
        return usernameHistoryRepository.findAll();
    }

    @Override
    public UsernameHistory updateUsernameHistory(UsernameHistory usernameHistory) {
        return usernameHistoryRepository.save(usernameHistory);
    }

    @Override
    public void deleteUsernameHistory(Long usernameHistoryId) {
        usernameHistoryRepository.deleteById(usernameHistoryId);
    }

    @Override
    public Optional<UsernameHistory> getUsernameHistoryByUserId(Long userId) {
        return usernameHistoryRepository.findUsernameHistoryByUserId(userId);
    }

    @Override
    public Optional<UsernameHistory> getUsernameHistoryByUsernameHistoryId(Long usernameHistoryId) {
        return usernameHistoryRepository.findById(usernameHistoryId);
    }

    @Override
    public void deleteUsernameHistoryByUserId(Long userId) {
        usernameHistoryRepository.deleteUsernameHistoryByUserId(userId);
    }
}
