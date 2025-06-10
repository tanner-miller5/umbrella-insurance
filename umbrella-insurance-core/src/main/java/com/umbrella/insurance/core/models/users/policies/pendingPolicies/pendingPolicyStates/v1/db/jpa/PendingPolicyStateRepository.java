package com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyStates.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.PendingPolicyState;

import java.util.Optional;

@Repository
public interface PendingPolicyStateRepository extends JpaRepository<PendingPolicyState, Long> {
    Optional<PendingPolicyState> findByPendingPolicyStateName(String pendingPolicyStateName);
    void deleteByPendingPolicyStateName(String pendingPolicyStateName);
}
