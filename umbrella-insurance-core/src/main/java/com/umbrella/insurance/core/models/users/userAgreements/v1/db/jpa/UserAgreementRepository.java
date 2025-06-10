package com.umbrella.insurance.core.models.users.userAgreements.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.UserAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAgreementRepository extends JpaRepository<UserAgreement, Long> {
    Optional<UserAgreement> findUserAgreementByUserIdAndUserAgreementName(
            Long userId, String userAgreementName);
    void deleteUserAgreementByUserId(Long userId);
    void deleteUserAgreementByUserIdAndUserAgreementName(Long userId, String userAgreementName);
    Optional<UserAgreement> findUserAgreementByUserId(Long userId);
}
