package com.umbrella.insurance.core.models.groups.groupUserRelationships.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.GroupUserRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupUserRelationshipServiceImpl implements GroupUserRelationshipService {

    @Autowired
    GroupUserRelationshipRepository groupUserRelationshipRepository;

    @Override
    public GroupUserRelationship saveGroupUserRelationship(GroupUserRelationship groupUserRelationship) {
        return groupUserRelationshipRepository.save(groupUserRelationship);
    }

    @Override
    public List<GroupUserRelationship> getGroupUserRelationships() {
        return groupUserRelationshipRepository.findAll();
    }

    @Override
    public GroupUserRelationship updateGroupUserRelationship(GroupUserRelationship groupUserRelationship) {
        return groupUserRelationshipRepository.save(groupUserRelationship);
    }

    @Override
    public void deleteGroupUserRelationship(Long groupUserRelationshipId) {
        groupUserRelationshipRepository.deleteById(groupUserRelationshipId);
    }

    @Override
    public Optional<GroupUserRelationship> getGroupUserRelationshipById(Long groupUserRelationshipId) {
        return groupUserRelationshipRepository.findById(groupUserRelationshipId);
    }

    @Override
    public Optional<GroupUserRelationship> getGroupUserRelationshipByGroupIdAndUserId(Long groupId, Long userId) {
        return groupUserRelationshipRepository.findByGroupIdAndUserId(groupId, userId);
    }

    @Override
    public void deleteGroupUserRelationshipByGroupIdAndUserId(Long groupId, Long userId) {
        groupUserRelationshipRepository.deleteGroupUserRelationshipByGroupIdAndUserId(groupId, userId);
    }

}
