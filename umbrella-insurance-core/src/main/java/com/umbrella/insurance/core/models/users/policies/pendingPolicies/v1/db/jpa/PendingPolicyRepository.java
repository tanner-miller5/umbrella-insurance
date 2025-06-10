package com.umbrella.insurance.core.models.users.policies.pendingPolicies.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.PendingPolicy;

import java.util.Optional;

@Repository
public interface PendingPolicyRepository extends JpaRepository<PendingPolicy, Long> {
    Optional<PendingPolicy> findByPendingPolicyName(String pendingPolicyName);
    void deleteByPendingPolicyName(String pendingPolicyName);
}
