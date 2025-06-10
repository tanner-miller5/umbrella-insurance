package com.umbrella.insurance.core.models.users.policies.pendingPolicies.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PendingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PendingPolicyServiceImpl implements PendingPolicyService {

    @Autowired
    PendingPolicyRepository pendingPolicyRepository;

    @Override
    public PendingPolicy savePendingPolicy(PendingPolicy pendingPolicy) {
        return pendingPolicyRepository.save(pendingPolicy);
    }

    @Override
    public List<PendingPolicy> getPendingPolicies() {
        return pendingPolicyRepository.findAll();
    }

    @Override
    public PendingPolicy updatePendingPolicy(PendingPolicy pendingPolicy) {
        return pendingPolicyRepository.save(pendingPolicy);
    }

    @Override
    public void deletePendingPolicy(Long pendingPolicyId) {
        pendingPolicyRepository.deleteById(pendingPolicyId);
    }

    @Override
    public Optional<PendingPolicy> getPendingPolicyByPendingPolicyName(String pendingPolicyName) {
        return pendingPolicyRepository.findByPendingPolicyName(pendingPolicyName);
    }

    @Override
    public Optional<PendingPolicy> getPendingPolicyById(Long pendingPolicyId) {
        return pendingPolicyRepository.findById(pendingPolicyId);
    }

    @Override
    public void deletePendingPolicyByPendingPolicyName(String pendingPolicyName) {
        pendingPolicyRepository.deleteByPendingPolicyName(pendingPolicyName);
    }
}
