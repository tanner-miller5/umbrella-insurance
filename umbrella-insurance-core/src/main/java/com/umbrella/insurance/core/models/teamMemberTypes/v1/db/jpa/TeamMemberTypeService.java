package com.umbrella.insurance.core.models.teamMemberTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamMemberType;

import java.util.List;
import java.util.Optional;

public interface TeamMemberTypeService {
    TeamMemberType saveTeamMemberType(TeamMemberType teamMemberType);
    List<TeamMemberType> getTeamMemberTypes();
    TeamMemberType updateTeamMemberType(TeamMemberType teamMemberType);
    void deleteTeamMemberType(Long teamMemberTypeId);
    Optional<TeamMemberType> getTeamMemberTypeByTeamMemberTypeName(String teamMemberTypeName);
    void deleteTeamMemberTypeByTeamMemberTypeName(String teamMemberTypeName);
    Optional<TeamMemberType> getTeamMemberTypeById(Long teamMemberTypeId);
}
