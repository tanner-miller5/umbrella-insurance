package com.umbrella.insurance.core.models.users.bets.matchedBets.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.MatchedBet;

import java.util.List;
import java.util.Optional;

public interface MatchedBetService {
    MatchedBet saveMatchedBet(MatchedBet matchedBet);
    List<MatchedBet> getMatchedBets();
    MatchedBet updateMatchedBet(MatchedBet matchedBet);
    void deleteMatchedBet(Long matchedBetId);
    Optional<MatchedBet> getMatchedBetByPendingBetId1(Long pendingBetId1);
    Optional<MatchedBet> getMatchedBetByMatchedBetId(Long matchedBetId);
    void deleteMatchedBetByPendingBetId(Long pendingBetId);
}
