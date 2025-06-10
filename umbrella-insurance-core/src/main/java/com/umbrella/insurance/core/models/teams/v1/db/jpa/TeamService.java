package com.umbrella.insurance.core.models.teams.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    Team saveTeam(Team team);
    List<Team> getTeams();
    Team updateTeam(Team team);
    void deleteTeam(Long teamId);
    Optional<Team> getTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
            String teamName, Long competitionId, Long gameTypeId);
    void deleteTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
            String teamName, Long competitionId, Long gameTypeId);
    Optional<Team> getTeamById(Long teamId);

}
