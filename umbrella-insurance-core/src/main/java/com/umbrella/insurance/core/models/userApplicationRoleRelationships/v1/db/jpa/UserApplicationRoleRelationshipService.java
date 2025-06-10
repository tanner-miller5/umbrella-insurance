package com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.UserApplicationRoleRelationship;

import java.util.List;
import java.util.Optional;

public interface UserApplicationRoleRelationshipService {
    UserApplicationRoleRelationship saveUserApplicationRoleRelationship(UserApplicationRoleRelationship userApplicationRoleRelationship);
    List<UserApplicationRoleRelationship> getUserApplicationRoleRelationships();
    UserApplicationRoleRelationship updateUserApplicationRoleRelationship(UserApplicationRoleRelationship userApplicationRoleRelationship);
    void deleteUserApplicationRoleRelationship(Long userApplicationRoleRelationshipId);
    Optional<UserApplicationRoleRelationship> getUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
            Long userId, Long applicationRoleId);
    List<UserApplicationRoleRelationship> getUserApplicationRoleRelationshipsByUserId(Long userId);
    Optional<UserApplicationRoleRelationship> getUserApplicationRoleRelationshipByUserApplicationRoleRelationshipId(
            Long userApplicationRoleRelationshipId);
    void deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(Long userId, Long applicationRoleId);
}
