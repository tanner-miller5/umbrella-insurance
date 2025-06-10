package com.umbrella.insurance.core.models.groups.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umbrella.insurance.core.models.entities.Group;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Override
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group updateGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    public Optional<Group> getGroupByGroupName(String groupName) {
        return groupRepository.findByGroupName(groupName);
    }

    @Override
    public void deleteGroupByGroupName(String groupName) {
        groupRepository.deleteByGroupName(groupName);
    }

    @Override
    public Optional<Group> getGroupByGroupId(Long groupId) {
        return groupRepository.findById(groupId);
    }
}
