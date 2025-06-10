package com.umbrella.insurance.core.models.referrals.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Referral;

import java.util.List;
import java.util.Optional;

public interface ReferralService {
    Referral saveReferral(Referral referral);
    List<Referral> getReferrals();
    Referral updateReferral(Referral referral);
    void deleteReferral(Long referralId);
    Optional<Referral> getReferral(Long referralId);
    Optional<Referral> getReferralByReferralName(String referralName);
    void deleteReferralByReferralName(String referralName);
}
