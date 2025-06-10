package com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PendingPolicyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PendingPolicyTypeRepository extends JpaRepository<PendingPolicyType, Long> {
    Optional<PendingPolicyType> findByPendingPolicyTypeName(String pendingPolicyTypeName);
    void deleteByPendingPolicyTypeName(String pendingPolicyTypeName);
}
