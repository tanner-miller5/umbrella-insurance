package com.umbrella.insurance.core.models.teamTransactions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamTransactionServiceImpl implements TeamTransactionService {
    @Autowired
    TeamTransactionRepository teamTransactionRepository;

    @Override
    public TeamTransaction saveTeamTransaction(TeamTransaction teamTransaction) {
        return teamTransactionRepository.save(teamTransaction);
    }

    @Override
    public List<TeamTransaction> getTeamTransactions() {
        return teamTransactionRepository.findAll();
    }

    @Override
    public TeamTransaction updateTeamTransaction(TeamTransaction teamTransaction) {
        return teamTransactionRepository.save(teamTransaction);
    }

    @Override
    public void deleteTeamTransaction(Long teamTransactionId) {
        teamTransactionRepository.deleteById(teamTransactionId);
    }

    @Override
    public Optional<TeamTransaction> getTeamTransactionByTeamTransactionByDescriptionAndTeamId(
            String teamTransactionDescription, Long teamTransactionId) {
        return teamTransactionRepository.getTeamTransactionByDescriptionAndTeamId(
                teamTransactionDescription, teamTransactionId
        );
    }

    @Override
    public void deleteTeamTransactionByDescriptionAndTeamId(String teamTransactionDescription, Long teamTransactionId) {
        teamTransactionRepository.deleteTeamTransactionByDescriptionAndTeamId(teamTransactionDescription, teamTransactionId);
    }

    @Override
    public Optional<TeamTransaction> getTeamTransactionById(Long teamTransactionId) {
        return teamTransactionRepository.findById(teamTransactionId);
    }
}
