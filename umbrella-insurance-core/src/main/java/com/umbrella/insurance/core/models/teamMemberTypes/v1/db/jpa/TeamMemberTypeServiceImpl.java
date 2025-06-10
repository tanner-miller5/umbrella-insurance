package com.umbrella.insurance.core.models.teamMemberTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamMemberType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamMemberTypeServiceImpl implements TeamMemberTypeService {
    @Autowired
    TeamMemberTypeRepository teamMemberTypeRepository;

    @Override
    public TeamMemberType saveTeamMemberType(TeamMemberType teamMemberType) {
        return teamMemberTypeRepository.save(teamMemberType);
    }

    @Override
    public List<TeamMemberType> getTeamMemberTypes() {
        return teamMemberTypeRepository.findAll();
    }

    @Override
    public TeamMemberType updateTeamMemberType(TeamMemberType teamMemberType) {
        return teamMemberTypeRepository.save(teamMemberType);
    }

    @Override
    public void deleteTeamMemberType(Long teamMemberTypeId) {
        teamMemberTypeRepository.deleteById(teamMemberTypeId);
    }

    @Override
    public Optional<TeamMemberType> getTeamMemberTypeByTeamMemberTypeName(String teamMemberTypeName) {
        return teamMemberTypeRepository.findByTeamMemberTypeName(teamMemberTypeName);
    }

    @Override
    public void deleteTeamMemberTypeByTeamMemberTypeName(String teamMemberTypeName) {
        teamMemberTypeRepository.deleteByTeamMemberTypeName(teamMemberTypeName);
    }

    @Override
    public Optional<TeamMemberType> getTeamMemberTypeById(Long teamMemberTypeId) {
        return teamMemberTypeRepository.findById(teamMemberTypeId);
    }
}
