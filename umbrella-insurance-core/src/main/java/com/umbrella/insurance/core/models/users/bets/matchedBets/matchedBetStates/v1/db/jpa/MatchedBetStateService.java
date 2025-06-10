package com.umbrella.insurance.core.models.users.bets.matchedBets.matchedBetStates.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.MatchedBetState;

import java.util.List;
import java.util.Optional;

public interface MatchedBetStateService {
    MatchedBetState saveMatchedBetState(MatchedBetState matchedBetState);
    List<MatchedBetState> getMatchedBetStates();
    MatchedBetState updateMatchedBetState(MatchedBetState matchedBetState);
    void deleteMatchedBetState(Long matchedBetStateId);
    Optional<MatchedBetState> getMatchedBetStateByMatchedBetStateName(String matchedBetStateName);
    void deleteMatchedBetStateByMatchedBetStateName(String matchedBetStateName);
    Optional<MatchedBetState> getMatchedBetStateByMatchedBetStateId(Long matchedBetStateId);
}
