package com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyStates.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PendingPolicyState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PendingPolicyStateServiceImpl implements PendingPolicyStateService {

    @Autowired
    PendingPolicyStateRepository pendingPolicyStateRepository;

    @Override
    public PendingPolicyState savePendingPolicyState(PendingPolicyState pendingPolicyState) {
        return pendingPolicyStateRepository.save(pendingPolicyState);
    }

    @Override
    public List<PendingPolicyState> getPendingPolicyStates() {
        return pendingPolicyStateRepository.findAll();
    }

    @Override
    public PendingPolicyState updatePendingPolicyState(PendingPolicyState pendingPolicyState) {
        return pendingPolicyStateRepository.save(pendingPolicyState);
    }

    @Override
    public void deletePendingPolicyState(Long pendingPolicyStateId) {
        pendingPolicyStateRepository.deleteById(pendingPolicyStateId);
    }

    @Override
    public Optional<PendingPolicyState> getPendingPolicyStateName(String pendingPolicyStateName) {
        return pendingPolicyStateRepository.findByPendingPolicyStateName(pendingPolicyStateName);
    }

    @Override
    public Optional<PendingPolicyState> getPendingPolicyStateById(Long pendingPolicyStateId) {
        return pendingPolicyStateRepository.findById(pendingPolicyStateId);
    }

    @Override
    public void deletePendingPolicyStateByPendingPolicyStateName(String pendingPolicyStateName) {
        pendingPolicyStateRepository.deleteByPendingPolicyStateName(pendingPolicyStateName);
    }
}
