package com.umbrella.insurance.core.models.referrals.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.Referral;

import java.util.Optional;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {
    Optional<Referral> findReferralByReferralName(String referralName);
    void deleteReferralByReferralName(String referralName);
}
