package com.umbrella.insurance.core.models.teams.teamTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamType;

import java.util.List;
import java.util.Optional;

public interface TeamTypeService {
    TeamType saveTeamType(TeamType teamType);
    List<TeamType> getTeamTypes();
    TeamType updateTeamType(TeamType teamType);
    void deleteTeamType(Long teamTypeId);
    Optional<TeamType> getTeamTypeByTeamTypeName(String teamTypeName);
    void deleteTeamTypeByTeamTypeName(String teamTypeName);
    Optional<TeamType> getTeamTypeById(Long teamTypeId);
}
