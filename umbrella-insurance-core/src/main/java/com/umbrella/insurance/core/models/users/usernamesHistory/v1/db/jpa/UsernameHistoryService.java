package com.umbrella.insurance.core.models.users.usernamesHistory.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.UsernameHistory;

import java.util.List;
import java.util.Optional;

public interface UsernameHistoryService {
    UsernameHistory saveUsernameHistory(UsernameHistory usernameHistory);
    List<UsernameHistory> getUsernameHistory();
    UsernameHistory updateUsernameHistory(UsernameHistory usernameHistory);
    void deleteUsernameHistory(Long usernameHistoryId);
    Optional<UsernameHistory> getUsernameHistoryByUserId(Long userId);
    Optional<UsernameHistory> getUsernameHistoryByUsernameHistoryId(Long usernameHistoryId);
    void deleteUsernameHistoryByUserId(Long userId);
}
