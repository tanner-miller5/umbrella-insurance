package com.umbrella.insurance.core.models.teams.teamTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamTypeServiceImpl implements TeamTypeService {
    @Autowired
    TeamTypeRepository teamTypeRepository;

    @Override
    public TeamType saveTeamType(TeamType teamType) {
        return teamTypeRepository.save(teamType);
    }

    @Override
    public List<TeamType> getTeamTypes() {
        return teamTypeRepository.findAll();
    }

    @Override
    public TeamType updateTeamType(TeamType teamType) {
        return teamTypeRepository.save(teamType);
    }

    @Override
    public void deleteTeamType(Long teamTypeId) {
        teamTypeRepository.deleteById(teamTypeId);
    }

    @Override
    public Optional<TeamType> getTeamTypeByTeamTypeName(String teamTypeName) {
        return teamTypeRepository.findByTeamTypeName(teamTypeName);
    }

    @Override
    public void deleteTeamTypeByTeamTypeName(String teamTypeName) {
        teamTypeRepository.deleteByTeamTypeName(teamTypeName);
    }

    @Override
    public Optional<TeamType> getTeamTypeById(Long teamTypeId) {
        return teamTypeRepository.findById(teamTypeId);
    }
}
