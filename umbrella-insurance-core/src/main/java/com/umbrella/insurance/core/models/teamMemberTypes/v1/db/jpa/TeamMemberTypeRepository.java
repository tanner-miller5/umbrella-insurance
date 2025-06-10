package com.umbrella.insurance.core.models.teamMemberTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamMemberType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMemberTypeRepository extends JpaRepository<TeamMemberType, Long> {
    Optional<TeamMemberType> findByTeamMemberTypeName(String teamMemberTypeName);
    void deleteByTeamMemberTypeName(String teamMemberTypeName);
}
