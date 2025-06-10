package com.umbrella.insurance.core.models.teamTransactions.teamTransactionTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamTransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamTransactionTypeServiceImpl  implements TeamTransactionTypeService {
    @Autowired
    TeamTransactionTypeRepository teamTransactionTypeRepository;

    @Override
    public TeamTransactionType saveTeamTransactionType(TeamTransactionType teamTransactionType) {
        return teamTransactionTypeRepository.save(teamTransactionType);
    }

    @Override
    public List<TeamTransactionType> getTeamTransactionTypes() {
        return teamTransactionTypeRepository.findAll();
    }

    @Override
    public TeamTransactionType updateTeamTransactionType(TeamTransactionType teamTransactionType) {
        return teamTransactionTypeRepository.save(teamTransactionType);
    }

    @Override
    public void deleteTeamTransactionType(Long teamTransactionTypeId) {
        teamTransactionTypeRepository.deleteById(teamTransactionTypeId);
    }

    @Override
    public Optional<TeamTransactionType> findTeamTransactionTypeByTeamTransactionTypeName(String teamTransactionTypeName) {
        return teamTransactionTypeRepository.findTeamTransactionTypeByTeamTransactionTypeName(
                teamTransactionTypeName);
    }

    @Override
    public void deleteTeamTransactionTypeByTeamTransactionTypeName(String teamTransactionTypeName) {
        teamTransactionTypeRepository.deleteTeamTransactionTypeByTeamTransactionTypeName(
                teamTransactionTypeName);
    }

    @Override
    public Optional<TeamTransactionType> findTeamTransactionTypeById(Long teamTransactionTypeId) {
        return teamTransactionTypeRepository.findById(teamTransactionTypeId);
    }
}
