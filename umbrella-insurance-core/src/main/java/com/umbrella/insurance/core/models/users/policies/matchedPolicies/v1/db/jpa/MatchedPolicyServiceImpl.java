package com.umbrella.insurance.core.models.users.policies.matchedPolicies.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.MatchedPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MatchedPolicyServiceImpl implements MatchedPolicyService {

    @Autowired
    MatchedPolicyRepository matchedPolicyRepository;

    @Override
    public MatchedPolicy saveMatchedPolicy(MatchedPolicy matchedPolicy) {
        return matchedPolicyRepository.save(matchedPolicy);
    }

    @Override
    public List<MatchedPolicy> getMatchedPolicies() {
        return matchedPolicyRepository.findAll();
    }

    @Override
    public MatchedPolicy updateMatchedPolicy(MatchedPolicy matchedPolicy) {
        return matchedPolicyRepository.save(matchedPolicy);
    }

    @Override
    public void deleteMatchedPolicy(Long matchedPolicyId) {
        matchedPolicyRepository.deleteById(matchedPolicyId);
    }

    @Override
    public Optional<MatchedPolicy> getMatchedPolicyByPendingInsurerPolicyId(Long pendingInsurerPolicyId) {
        return matchedPolicyRepository.getMatchedPolicyByPendingInsurerPolicyId(pendingInsurerPolicyId);
    }

    @Override
    public Optional<MatchedPolicy> getMatchedPolicyByMatchedPolicyId(Long matchedPolicyId) {
        return matchedPolicyRepository.findById(matchedPolicyId);
    }

    @Override
    public void deleteMatchedPolicyByPendingInsurerPolicyId(Long pendingInsurerPolicyId) {
        matchedPolicyRepository.deleteMatchedPolicyByPendingInsurerPolicyId(pendingInsurerPolicyId);
    }
}
