package com.umbrella.insurance.core.models.users.policies.pendingPolicies.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.PendingPolicy;

import java.util.List;
import java.util.Optional;

@Repository
public interface PendingPolicyRepository extends JpaRepository<PendingPolicy, Long> {
    Optional<PendingPolicy> findByPendingPolicyName(String pendingPolicyName);
    void deleteByPendingPolicyName(String pendingPolicyName);
    @Query(value = "select p.* from pending_policies p join sessions s on p.session_id=s.session_id \n" +
            "join Users u on s.user_id = u.user_id where u.user_id = :userId", nativeQuery = true)
    List<PendingPolicy> getPendingPoliciesByUserId(Long userId);
}
