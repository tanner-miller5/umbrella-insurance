package com.umbrella.insurance.core.models.teams.teamTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamTypeRepository extends JpaRepository<TeamType, Long> {
    Optional<TeamType> findByTeamTypeName(String teamTypeName);
    void deleteByTeamTypeName(String teamTypeName);
}
