package com.umbrella.insurance.core.models.users.bets.pendingBets.pendingBetStates.v1.db.jpa;

import java.util.List;
import java.util.Optional;

import com.umbrella.insurance.core.models.entities.PendingBetState;

public interface PendingBetStateService {
    PendingBetState savePendingBetState(PendingBetState pendingBetState);
    List<PendingBetState> getPendingBetStates();
    PendingBetState updatePendingBetState(PendingBetState pendingBetState);
    void deletePendingBetState(Long pendingBetStateId);
    Optional<PendingBetState> getPendingBetStateByPendingBetStateName(String pendingBetStateName);
    void deletePendingBetStateByPendingBetStateName(String pendingBetStateName);
    Optional<PendingBetState> getPendingBetStateByPendingBetStateId(Long pendingBetStateId);
}
