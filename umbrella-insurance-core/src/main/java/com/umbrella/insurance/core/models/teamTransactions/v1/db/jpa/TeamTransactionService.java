package com.umbrella.insurance.core.models.teamTransactions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamTransaction;

import java.util.List;
import java.util.Optional;

public interface TeamTransactionService {
    TeamTransaction saveTeamTransaction(TeamTransaction teamTransaction);
    List<TeamTransaction> getTeamTransactions();
    TeamTransaction updateTeamTransaction(TeamTransaction teamTransaction);
    void deleteTeamTransaction(Long teamTransactionId);
    Optional<TeamTransaction> getTeamTransactionByTeamTransactionByDescriptionAndTeamId(
            String teamTransactionDescription, Long teamTransactionId
    );
    void deleteTeamTransactionByDescriptionAndTeamId(String teamTransactionDescription, Long teamTransactionId);
    Optional<TeamTransaction> getTeamTransactionById(Long teamTransactionId);
}
