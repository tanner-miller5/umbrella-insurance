package com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.VerificationMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationMethodRepository extends JpaRepository<VerificationMethod, Long> {
    Optional<VerificationMethod> getVerificationMethodByVerificationMethodName(String verificationMethodName);
    void deleteVerificationMethodByVerificationMethodName(String verificationMethodName);
}
