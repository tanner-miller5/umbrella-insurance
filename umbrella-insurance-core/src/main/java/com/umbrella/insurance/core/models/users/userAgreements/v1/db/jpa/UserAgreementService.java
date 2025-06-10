package com.umbrella.insurance.core.models.users.userAgreements.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.UserAgreement;

import java.util.List;
import java.util.Optional;

public interface UserAgreementService {
    UserAgreement saveUserAgreement(UserAgreement userAgreement);
    List<UserAgreement> getUserAgreements();
    UserAgreement updateUserAgreement(UserAgreement userAgreement);
    void deleteUserAgreement(Long userAgreementId);
    Optional<UserAgreement> getUserAgreementByUserIdAndUserAgreementName(
            Long userId, String userAgreementName);
    void deleteUserAgreementByUserId(Long userId);
    void deleteUserAgreementByUserIdAndUserAgreementName(Long userId, String userAgreementName);
    Optional<UserAgreement> findUserAgreementByUserId(Long userId);
    Optional<UserAgreement> findUserAgreementByUserAgreementId(Long userAgreementId);
}
