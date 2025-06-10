package com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.VerificationMethod;

import java.util.List;
import java.util.Optional;

public interface VerificationMethodService {
    VerificationMethod saveVerificationMethod(VerificationMethod verificationMethod);
    List<VerificationMethod> getVerificationMethods();
    VerificationMethod updateVerificationMethod(VerificationMethod verificationMethod);
    void deleteVerificationMethod(Long verificationMethodId);
    Optional<VerificationMethod> getVerificationMethodByVerificationMethodName(
            String verificationMethodName);
    Optional<VerificationMethod> getVerificationMethodByVerificationMethodId(Long verificationMethodId);
    void deleteVerificationMethodByVerificationMethodName(String verificationMethodName);
}
