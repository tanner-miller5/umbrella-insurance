package com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.UserApplicationRoleRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserApplicationRoleRelationshipServiceImpl implements UserApplicationRoleRelationshipService {
    @Autowired
    UserApplicationRoleRelationshipRepository userApplicationRoleRelationshipRepository;

    @Override
    public UserApplicationRoleRelationship saveUserApplicationRoleRelationship(
            UserApplicationRoleRelationship userApplicationRoleRelationship) {
        return userApplicationRoleRelationshipRepository.save(userApplicationRoleRelationship);
    }

    @Override
    public List<UserApplicationRoleRelationship> getUserApplicationRoleRelationships() {
        return userApplicationRoleRelationshipRepository.findAll();
    }

    @Override
    public UserApplicationRoleRelationship updateUserApplicationRoleRelationship(
            UserApplicationRoleRelationship userApplicationRoleRelationship) {
        return userApplicationRoleRelationshipRepository.save(userApplicationRoleRelationship);
    }

    @Override
    public void deleteUserApplicationRoleRelationship(Long userApplicationRoleRelationshipId) {
        userApplicationRoleRelationshipRepository.deleteById(userApplicationRoleRelationshipId);
    }

    @Override
    public Optional<UserApplicationRoleRelationship> getUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
            Long userId, Long applicationRoleId) {
        return userApplicationRoleRelationshipRepository.getUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                userId, applicationRoleId);
    }

    @Override
    public List<UserApplicationRoleRelationship> getUserApplicationRoleRelationshipsByUserId(Long userId) {
        return userApplicationRoleRelationshipRepository.getUserApplicationRoleRelationshipsByUserId(userId);
    }

    @Override
    public Optional<UserApplicationRoleRelationship> getUserApplicationRoleRelationshipByUserApplicationRoleRelationshipId(Long userApplicationRoleRelationshipId) {
        return userApplicationRoleRelationshipRepository.findById(
                userApplicationRoleRelationshipId);
    }

    @Override
    public void deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(Long userId, Long applicationRoleId) {
        userApplicationRoleRelationshipRepository.deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                userId, applicationRoleId);
    }
}
