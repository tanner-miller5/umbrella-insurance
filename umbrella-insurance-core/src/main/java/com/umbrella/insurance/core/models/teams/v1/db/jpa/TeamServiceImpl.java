package com.umbrella.insurance.core.models.teams.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.umbrella.insurance.core.models.entities.Team;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamRepository teamRepository;

    @Override
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team updateTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }

    @Override
    public Optional<Team> getTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(String teamName, Long competitionId, Long gameTypeId) {
        return teamRepository.getTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
                teamName, competitionId, gameTypeId);
    }

    @Override
    public void deleteTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
            String teamName, Long competitionId, Long gameTypeId) {
        teamRepository.deleteTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
                teamName, competitionId, gameTypeId);
    }

    @Override
    public Optional<Team> getTeamById(Long teamId) {
        return teamRepository.findById(teamId);
    }
}
