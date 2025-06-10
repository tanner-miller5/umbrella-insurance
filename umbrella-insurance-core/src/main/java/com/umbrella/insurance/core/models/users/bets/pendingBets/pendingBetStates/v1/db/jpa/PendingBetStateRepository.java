package com.umbrella.insurance.core.models.users.bets.pendingBets.pendingBetStates.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.PendingBetState;

import java.util.Optional;

@Repository
public interface PendingBetStateRepository extends JpaRepository<PendingBetState, Long> {
    Optional<PendingBetState> getPendingBetStateByPendingBetStateName(String pendingBetStateName);
    void deletePendingBetStateByPendingBetStateName(String pendingBetStateName);
}
