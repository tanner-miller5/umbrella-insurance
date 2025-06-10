package com.umbrella.insurance.core.models.users.policies.matchedPolicies.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.MatchedPolicy;

import java.util.List;
import java.util.Optional;

public interface MatchedPolicyService {
    MatchedPolicy saveMatchedPolicy(MatchedPolicy matchedPolicy);
    List<MatchedPolicy> getMatchedPolicies();
    MatchedPolicy updateMatchedPolicy(MatchedPolicy matchedPolicy);
    void deleteMatchedPolicy(Long matchedPolicyId);
    Optional<MatchedPolicy> getMatchedPolicyByPendingInsurerPolicyId(Long pendingInsurerPolicyId);
    Optional<MatchedPolicy> getMatchedPolicyByMatchedPolicyId(Long matchedPolicyId);
    void deleteMatchedPolicyByPendingInsurerPolicyId(Long pendingInsurerPolicyId);
}
