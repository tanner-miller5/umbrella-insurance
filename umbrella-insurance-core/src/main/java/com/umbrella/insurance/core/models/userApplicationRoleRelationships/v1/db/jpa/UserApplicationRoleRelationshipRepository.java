package com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.UserApplicationRoleRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserApplicationRoleRelationshipRepository extends JpaRepository<UserApplicationRoleRelationship, Long> {
    Optional<UserApplicationRoleRelationship> getUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
            Long userId, Long applicationRoleId);
    List<UserApplicationRoleRelationship> getUserApplicationRoleRelationshipsByUserId(Long userId);
    void deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(Long userId, Long applicationRoleId);
}
