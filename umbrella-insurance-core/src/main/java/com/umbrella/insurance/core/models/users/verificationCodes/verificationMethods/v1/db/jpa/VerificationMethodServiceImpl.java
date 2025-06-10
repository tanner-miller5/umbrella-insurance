package com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.VerificationMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VerificationMethodServiceImpl implements VerificationMethodService {

    @Autowired
    VerificationMethodRepository verificationMethodRepository;

    @Override
    public VerificationMethod saveVerificationMethod(VerificationMethod verificationMethod) {
        return verificationMethodRepository.save(verificationMethod);
    }

    @Override
    public List<VerificationMethod> getVerificationMethods() {
        return verificationMethodRepository.findAll();
    }

    @Override
    public VerificationMethod updateVerificationMethod(VerificationMethod verificationMethod) {
        return verificationMethodRepository.save(verificationMethod);
    }

    @Override
    public void deleteVerificationMethod(Long verificationMethodId) {
        verificationMethodRepository.deleteById(verificationMethodId);
    }

    @Override
    public Optional<VerificationMethod> getVerificationMethodByVerificationMethodName(String verificationMethodName) {
        return verificationMethodRepository.getVerificationMethodByVerificationMethodName(verificationMethodName);
    }

    @Override
    public Optional<VerificationMethod> getVerificationMethodByVerificationMethodId(Long verificationMethodId) {
        return verificationMethodRepository.findById(verificationMethodId);
    }

    @Override
    public void deleteVerificationMethodByVerificationMethodName(String verificationMethodName) {
        verificationMethodRepository.deleteVerificationMethodByVerificationMethodName(verificationMethodName);
    }
}
