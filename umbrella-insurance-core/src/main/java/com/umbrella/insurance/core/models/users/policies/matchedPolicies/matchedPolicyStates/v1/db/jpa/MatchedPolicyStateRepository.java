package com.umbrella.insurance.core.models.users.policies.matchedPolicies.matchedPolicyStates.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.MatchedPolicyState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchedPolicyStateRepository extends JpaRepository<MatchedPolicyState, Long> {
    Optional<MatchedPolicyState> findByMatchedPolicyStateName(String matchedPolicyStateName);
    void deleteByMatchedPolicyStateName(String matchedPolicyStateName);
}
