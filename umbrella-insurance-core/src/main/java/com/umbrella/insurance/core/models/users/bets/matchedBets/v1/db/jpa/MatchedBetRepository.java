package com.umbrella.insurance.core.models.users.bets.matchedBets.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.MatchedBet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchedBetRepository extends JpaRepository<MatchedBet, Long> {
    Optional<MatchedBet> findByPendingBet1Id(Long pendingBet1);
    void deleteByPendingBet1Id(Long pendingBet1Id);
}
