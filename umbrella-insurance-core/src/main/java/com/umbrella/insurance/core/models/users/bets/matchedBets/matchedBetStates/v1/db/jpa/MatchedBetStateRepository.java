package com.umbrella.insurance.core.models.users.bets.matchedBets.matchedBetStates.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.MatchedBetState;

import java.util.Optional;

@Repository
public interface MatchedBetStateRepository extends JpaRepository<MatchedBetState, Long> {
    Optional<MatchedBetState> findMatchedBetStateByMatchedBetStateName(String matchedBetStateName);
    void deleteMatchedBetStateByMatchedBetStateName(String matchedBetStateName);
}
