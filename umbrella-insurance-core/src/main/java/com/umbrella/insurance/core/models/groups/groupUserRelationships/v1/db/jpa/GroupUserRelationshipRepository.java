package com.umbrella.insurance.core.models.groups.groupUserRelationships.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.GroupUserRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupUserRelationshipRepository extends JpaRepository<GroupUserRelationship, Long> {
    Optional<GroupUserRelationship> findByGroupIdAndUserId(Long groupId, Long userId);
    void deleteGroupUserRelationshipByGroupIdAndUserId(Long groupId, Long userId);
}
