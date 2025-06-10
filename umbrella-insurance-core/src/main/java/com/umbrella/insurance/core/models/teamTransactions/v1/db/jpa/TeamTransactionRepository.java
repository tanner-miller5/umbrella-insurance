package com.umbrella.insurance.core.models.teamTransactions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamTransactionRepository extends JpaRepository<TeamTransaction, Long> {
    Optional<TeamTransaction> getTeamTransactionByDescriptionAndTeamId(
            String description, Long teamTransactionId);
    void deleteTeamTransactionByDescriptionAndTeamId(String description, Long teamTransactionId);
}
