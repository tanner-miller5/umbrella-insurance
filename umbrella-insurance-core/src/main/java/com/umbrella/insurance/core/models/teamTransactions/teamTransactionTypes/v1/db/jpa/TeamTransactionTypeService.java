package com.umbrella.insurance.core.models.teamTransactions.teamTransactionTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamTransactionType;

import java.util.List;
import java.util.Optional;

public interface TeamTransactionTypeService {
    TeamTransactionType saveTeamTransactionType(TeamTransactionType teamTransactionType);
    List<TeamTransactionType> getTeamTransactionTypes();
    TeamTransactionType updateTeamTransactionType(TeamTransactionType teamTransactionType);
    void deleteTeamTransactionType(Long teamTransactionTypeId);
    Optional<TeamTransactionType> findTeamTransactionTypeByTeamTransactionTypeName(
            String teamTransactionTypeName);
    void deleteTeamTransactionTypeByTeamTransactionTypeName(String teamTransactionTypeName);
    Optional<TeamTransactionType> findTeamTransactionTypeById(Long teamTransactionTypeId);
}
