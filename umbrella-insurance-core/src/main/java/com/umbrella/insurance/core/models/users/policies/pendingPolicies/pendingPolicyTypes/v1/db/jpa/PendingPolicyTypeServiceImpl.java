package com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PendingPolicyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PendingPolicyTypeServiceImpl implements PendingPolicyTypeService {

    @Autowired
    PendingPolicyTypeRepository pendingPolicyTypeRepository;

    @Override
    public PendingPolicyType savePendingPolicyType(PendingPolicyType pendingPolicyType) {
        return pendingPolicyTypeRepository.save(pendingPolicyType);
    }

    @Override
    public List<PendingPolicyType> getPendingPolicyTypes() {
        return pendingPolicyTypeRepository.findAll();
    }

    @Override
    public PendingPolicyType updatePendingPolicyType(PendingPolicyType pendingPolicyType) {
        return pendingPolicyTypeRepository.save(pendingPolicyType);
    }

    @Override
    public void deletePendingPolicyType(Long pendingPolicyTypeId) {
        pendingPolicyTypeRepository.deleteById(pendingPolicyTypeId);
    }

    @Override
    public Optional<PendingPolicyType> getPendingPolicyTypeByPendingPolicyTypeName(String pendingPolicyTypeName) {
        return pendingPolicyTypeRepository.findByPendingPolicyTypeName(pendingPolicyTypeName);
    }

    @Override
    public Optional<PendingPolicyType> getPendingPolicyTypeById(Long pendingPolicyTypeId) {
        return pendingPolicyTypeRepository.findById(pendingPolicyTypeId);
    }

    @Override
    public void deletePendingPolicyTypeByPendingPolicyTypeName(String pendingPolicyTypeName) {
        pendingPolicyTypeRepository.deleteByPendingPolicyTypeName(pendingPolicyTypeName);
    }
}
