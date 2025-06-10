package com.umbrella.insurance.core.models.users.policies.matchedPolicies.matchedPolicyStates.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.MatchedPolicyState;

import java.util.List;
import java.util.Optional;

public interface MatchedPolicyStateService {
    MatchedPolicyState saveMatchedPolicyState(MatchedPolicyState matchedPolicyState);
    List<MatchedPolicyState> getMatchedPolicyStates();
    MatchedPolicyState updateMatchedPolicyState(MatchedPolicyState matchedPolicyState);
    void deleteMatchedPolicyState(Long matchedPolicyStateId);
    Optional<MatchedPolicyState> getMatchedPolicyStateByMatchedPolicyStateName(String matchedPolicyStateName);
    Optional<MatchedPolicyState> getMatchedPolicyStateByMatchedPolicyStateId(Long matchedPolicyStateId);
    void deleteMatchedPolicyStateByMatchedPolicyStateName(String matchedPolicyStateName);
}
