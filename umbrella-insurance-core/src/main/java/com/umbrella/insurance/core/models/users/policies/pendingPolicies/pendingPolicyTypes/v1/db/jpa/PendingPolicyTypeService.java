package com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PendingPolicyType;

import java.util.List;
import java.util.Optional;

public interface PendingPolicyTypeService {
    PendingPolicyType savePendingPolicyType(PendingPolicyType pendingPolicyType);
    List<PendingPolicyType> getPendingPolicyTypes();
    PendingPolicyType updatePendingPolicyType(PendingPolicyType pendingPolicyType);
    void deletePendingPolicyType(Long pendingPolicyTypeId);
    Optional<PendingPolicyType> getPendingPolicyTypeByPendingPolicyTypeName(String pendingPolicyTypeName);
    Optional<PendingPolicyType> getPendingPolicyTypeById(Long pendingPolicyTypeId);
    void deletePendingPolicyTypeByPendingPolicyTypeName(String pendingPolicyTypeName);
}
