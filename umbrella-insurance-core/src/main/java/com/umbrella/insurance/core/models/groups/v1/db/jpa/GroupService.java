package com.umbrella.insurance.core.models.groups.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    Group saveGroup(Group group);
    List<Group> getGroups();
    Group updateGroup(Group group);
    void deleteGroup(Long groupId);
    Optional<Group> getGroupByGroupName(String groupName);
    void deleteGroupByGroupName(String groupName);
    Optional<Group> getGroupByGroupId(Long groupId);
}
