package com.umbrella.insurance.core.models.users.verificationCodes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.VerificationCode;

import java.util.List;
import java.util.Optional;

public interface VerificationCodeService {
    VerificationCode saveVerificationCode(VerificationCode verificationCode);
    List<VerificationCode> getVerificationCodes();
    VerificationCode updateVerificationCode(VerificationCode verificationCode);
    void deleteVerificationCode(Long verificationCodeId);
    Optional<VerificationCode> getVerificationCodeByUserId(Long userId);
    Optional<VerificationCode> getVerificationCodeByVerificationCodeId(Long verificationCodeId);
    void deleteVerificationCodeByUserId(Long userId);
}
