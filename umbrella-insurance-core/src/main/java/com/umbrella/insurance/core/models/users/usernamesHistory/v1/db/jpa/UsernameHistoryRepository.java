package com.umbrella.insurance.core.models.users.usernamesHistory.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.UsernameHistory;

import java.util.Optional;

@Repository
public interface UsernameHistoryRepository extends JpaRepository<UsernameHistory, Long> {
    Optional<UsernameHistory> findUsernameHistoryByUserId(Long userId);
    void deleteUsernameHistoryByUserId(Long userId);
}
