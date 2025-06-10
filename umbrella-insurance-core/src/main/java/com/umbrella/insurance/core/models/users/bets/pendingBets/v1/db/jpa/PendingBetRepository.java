package com.umbrella.insurance.core.models.users.bets.pendingBets.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PendingBet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PendingBetRepository extends JpaRepository<PendingBet, Long> {
    Optional<PendingBet> findPendingBetByPendingBetName(String pendingBetName);
    void deletePendingBetByPendingBetName(String pendingBetName);
}
