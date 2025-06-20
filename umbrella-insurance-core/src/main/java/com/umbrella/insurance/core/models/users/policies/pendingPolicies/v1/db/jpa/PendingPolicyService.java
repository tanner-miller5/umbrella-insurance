package com.umbrella.insurance.core.models.users.policies.pendingPolicies.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PendingPolicy;

import java.util.List;
import java.util.Optional;

public interface PendingPolicyService {
    PendingPolicy savePendingPolicy(PendingPolicy pendingPolicy);
    List<PendingPolicy> getPendingPolicies();
    PendingPolicy updatePendingPolicy(PendingPolicy pendingPolicy);
    void deletePendingPolicy(Long pendingPolicyId);
    Optional<PendingPolicy> getPendingPolicyByPendingPolicyName(String pendingPolicyName);
    Optional<PendingPolicy> getPendingPolicyById(Long pendingPolicyId);
    void deletePendingPolicyByPendingPolicyName(String pendingPolicyName);
    List<PendingPolicy> getPendingPoliciesByUserId(Long userId);

}
