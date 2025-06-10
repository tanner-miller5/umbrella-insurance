package com.umbrella.insurance.core.models.users.policies.matchedPolicies.matchedPolicyStates.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.MatchedPolicyState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MatchedPolicyStateServiceImpl implements MatchedPolicyStateService {

    @Autowired
    MatchedPolicyStateRepository matchedPolicyStateRepository;

    @Override
    public MatchedPolicyState saveMatchedPolicyState(MatchedPolicyState matchedPolicyState) {
        return matchedPolicyStateRepository.save(matchedPolicyState);
    }

    @Override
    public List<MatchedPolicyState> getMatchedPolicyStates() {
        return matchedPolicyStateRepository.findAll();
    }

    @Override
    public MatchedPolicyState updateMatchedPolicyState(MatchedPolicyState matchedPolicyState) {
        return matchedPolicyStateRepository.save(matchedPolicyState);
    }

    @Override
    public void deleteMatchedPolicyState(Long matchedPolicyStateId) {
        matchedPolicyStateRepository.deleteById(matchedPolicyStateId);
    }

    @Override
    public Optional<MatchedPolicyState> getMatchedPolicyStateByMatchedPolicyStateName(String matchedPolicyStateName) {
        return matchedPolicyStateRepository.findByMatchedPolicyStateName(matchedPolicyStateName);
    }

    @Override
    public Optional<MatchedPolicyState> getMatchedPolicyStateByMatchedPolicyStateId(Long matchedPolicyStateId) {
        return matchedPolicyStateRepository.findById(matchedPolicyStateId);
    }

    @Override
    public void deleteMatchedPolicyStateByMatchedPolicyStateName(String matchedPolicyStateName) {
        matchedPolicyStateRepository.deleteByMatchedPolicyStateName(matchedPolicyStateName);
    }
}
