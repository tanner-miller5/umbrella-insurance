package com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyStates.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PendingPolicyState;

import java.util.List;
import java.util.Optional;

public interface PendingPolicyStateService {
    PendingPolicyState savePendingPolicyState(PendingPolicyState pendingPolicyState);
    List<PendingPolicyState> getPendingPolicyStates();
    PendingPolicyState updatePendingPolicyState(PendingPolicyState pendingPolicyState);
    void deletePendingPolicyState(Long pendingPolicyStateId);
    Optional<PendingPolicyState> getPendingPolicyStateName(String pendingPolicyStateName);
    Optional<PendingPolicyState> getPendingPolicyStateById(Long pendingPolicyStateId);
    void deletePendingPolicyStateByPendingPolicyStateName(String pendingPolicyStateName);
}
