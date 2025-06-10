package com.umbrella.insurance.core.models.users.verificationCodes.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.VerificationCode;

import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    @Query(value = "SELECT * FROM verification_codes v WHERE v.user_id = :userId ORDER BY v.verification_code_id desc limit 1", nativeQuery = true)
    Optional<VerificationCode> findVerificationCodeByUserId(Long userId);
    void deleteVerificationCodeByUserId(Long userId);
}
