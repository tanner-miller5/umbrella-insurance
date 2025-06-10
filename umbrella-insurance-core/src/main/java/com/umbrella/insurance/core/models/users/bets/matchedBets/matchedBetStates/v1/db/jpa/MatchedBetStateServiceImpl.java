package com.umbrella.insurance.core.models.users.bets.matchedBets.matchedBetStates.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.MatchedBetState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MatchedBetStateServiceImpl implements MatchedBetStateService {
    @Autowired
    MatchedBetStateRepository matchedBetStateRepository;

    @Override
    public MatchedBetState saveMatchedBetState(MatchedBetState matchedBetState) {
        return matchedBetStateRepository.save(matchedBetState);
    }

    @Override
    public List<MatchedBetState> getMatchedBetStates() {
        return matchedBetStateRepository.findAll();
    }

    @Override
    public MatchedBetState updateMatchedBetState(MatchedBetState matchedBetState) {
        return matchedBetStateRepository.save(matchedBetState);
    }

    @Override
    public void deleteMatchedBetState(Long matchedBetStateId) {
        matchedBetStateRepository.deleteById(matchedBetStateId);
    }

    @Override
    public Optional<MatchedBetState> getMatchedBetStateByMatchedBetStateName(String matchedBetStateName) {
        return matchedBetStateRepository.findMatchedBetStateByMatchedBetStateName(matchedBetStateName);
    }

    @Override
    public void deleteMatchedBetStateByMatchedBetStateName(String matchedBetStateName) {
        matchedBetStateRepository.deleteMatchedBetStateByMatchedBetStateName(matchedBetStateName);
    }

    @Override
    public Optional<MatchedBetState> getMatchedBetStateByMatchedBetStateId(Long matchedBetStateId) {
        return matchedBetStateRepository.findById(matchedBetStateId);
    }
}
