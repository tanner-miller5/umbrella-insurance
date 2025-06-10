package com.umbrella.insurance.core.models.referrals.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Referral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReferralServiceImpl implements ReferralService {
    @Autowired
    ReferralRepository referralRepository;

    @Override
    public Referral saveReferral(Referral referral) {
        return referralRepository.save(referral);
    }

    @Override
    public List<Referral> getReferrals() {
        return referralRepository.findAll();
    }

    @Override
    public Referral updateReferral(Referral referral) {
        return referralRepository.save(referral);
    }

    @Override
    public void deleteReferral(Long referralId) {
        referralRepository.deleteById(referralId);
    }

    @Override
    public Optional<Referral> getReferral(Long referralId) {
        return referralRepository.findById(referralId);
    }

    @Override
    public Optional<Referral> getReferralByReferralName(String referralName) {
        return referralRepository.findReferralByReferralName(referralName);
    }

    @Override
    public void deleteReferralByReferralName(String referralName) {
        referralRepository.deleteReferralByReferralName(referralName);
    }
}
