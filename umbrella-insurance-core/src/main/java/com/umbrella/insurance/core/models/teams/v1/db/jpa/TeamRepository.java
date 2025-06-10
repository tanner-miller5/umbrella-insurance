package com.umbrella.insurance.core.models.teams.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> getTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
            String name, Long competitionId, Long gameTypeId);
    void deleteTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
            String TeamName, Long competitionId, Long gameTypeId);
}
