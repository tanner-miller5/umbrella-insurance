package com.umbrella.insurance.core.models.users.userAgreements.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.UserAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserAgreementServiceImpl implements UserAgreementService {

    @Autowired
    UserAgreementRepository userAgreementRepository;

    @Override
    public UserAgreement saveUserAgreement(UserAgreement userAgreement) {
        return userAgreementRepository.save(userAgreement);
    }

    @Override
    public List<UserAgreement> getUserAgreements() {
        return userAgreementRepository.findAll();
    }

    @Override
    public UserAgreement updateUserAgreement(UserAgreement userAgreement) {
        return userAgreementRepository.save(userAgreement);
    }

    @Override
    public void deleteUserAgreement(Long userAgreementId) {
        userAgreementRepository.deleteById(userAgreementId);
    }

    @Override
    public Optional<UserAgreement> getUserAgreementByUserIdAndUserAgreementName(Long userId, String userAgreementName) {
        return userAgreementRepository.findUserAgreementByUserIdAndUserAgreementName(
                userId, userAgreementName);
    }

    @Override
    public void deleteUserAgreementByUserId(Long userId) {
        userAgreementRepository.deleteUserAgreementByUserId(userId);
    }

    @Override
    public void deleteUserAgreementByUserIdAndUserAgreementName(Long userId, String userAgreementName) {
        userAgreementRepository.deleteUserAgreementByUserIdAndUserAgreementName(userId, userAgreementName);
    }

    @Override
    public Optional<UserAgreement> findUserAgreementByUserId(Long userId) {
        return userAgreementRepository.findUserAgreementByUserId(userId);
    }

    @Override
    public Optional<UserAgreement> findUserAgreementByUserAgreementId(Long userAgreementId) {
        return userAgreementRepository.findById(userAgreementId);
    }
}
