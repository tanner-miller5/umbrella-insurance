package com.umbrella.insurance.core.models.users.totps.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Totp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TotpRepository extends JpaRepository<Totp, Long> {
    Optional<Totp> findTotpByUserId(Long userId);
    void deleteTotpByUserId(Long userId);
}
