package com.umbrella.insurance.core.models.groups.groupUserRelationships.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.GroupUserRelationship;
import java.util.List;
import java.util.Optional;

public interface GroupUserRelationshipService {
    GroupUserRelationship saveGroupUserRelationship(GroupUserRelationship groupUserRelationship);
    List<GroupUserRelationship> getGroupUserRelationships();
    GroupUserRelationship updateGroupUserRelationship(GroupUserRelationship groupUserRelationship);
    void deleteGroupUserRelationship(Long groupUserRelationshipId);
    Optional<GroupUserRelationship> getGroupUserRelationshipById(Long groupUserRelationshipId);
    Optional<GroupUserRelationship> getGroupUserRelationshipByGroupIdAndUserId(Long groupId, Long userId);
    void deleteGroupUserRelationshipByGroupIdAndUserId(Long groupId, Long userId);
}
