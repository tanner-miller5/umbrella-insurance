package com.umbrella.insurance.core.models.groups.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupName(String groupName);
    void deleteByGroupName(String groupName);
}
