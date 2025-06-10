package com.umbrella.insurance.core.models.users.bets.pendingBets.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PendingBet;

import java.util.List;
import java.util.Optional;

public interface PendingBetService {
    PendingBet savePendingBet(PendingBet pendingBet);
    List<PendingBet> getPendingBets();
    PendingBet updatePendingBet(PendingBet pendingBet);
    void deletePendingBet(Long pendingBetId);
    Optional<PendingBet> getPendingBetByPendingBetName(String pendingBetName);
    Optional<PendingBet> getPendingBetByPendingBetId(Long pendingBetId);
    void deletePendingBetByPendingBetName(String pendingBetName);
}
