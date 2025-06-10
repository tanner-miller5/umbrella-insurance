package com.umbrella.insurance.core.models.teamTransactions.teamTransactionTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamTransactionTypeRepository extends JpaRepository<TeamTransactionType, Long> {
    Optional<TeamTransactionType> findTeamTransactionTypeByTeamTransactionTypeName(
            String teamTransactionTypeName);
    void deleteTeamTransactionTypeByTeamTransactionTypeName(String teamTransactionTypeName);
}
