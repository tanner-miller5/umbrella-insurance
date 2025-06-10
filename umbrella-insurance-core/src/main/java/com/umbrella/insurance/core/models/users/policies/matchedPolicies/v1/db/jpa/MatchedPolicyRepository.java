package com.umbrella.insurance.core.models.users.policies.matchedPolicies.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.MatchedPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchedPolicyRepository  extends JpaRepository<MatchedPolicy, Long> {
    Optional<MatchedPolicy> getMatchedPolicyByPendingInsurerPolicyId(Long pendingInsurerPolicyId);
    void deleteMatchedPolicyByPendingInsurerPolicyId(Long pendingInsurerPolicyId);
}
