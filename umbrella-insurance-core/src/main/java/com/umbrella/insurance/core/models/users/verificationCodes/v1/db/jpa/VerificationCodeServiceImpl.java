package com.umbrella.insurance.core.models.users.verificationCodes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode saveVerificationCode(VerificationCode verificationCode) {
        return verificationCodeRepository.save(verificationCode);
    }

    @Override
    public List<VerificationCode> getVerificationCodes() {
        return verificationCodeRepository.findAll();
    }

    @Override
    public VerificationCode updateVerificationCode(VerificationCode verificationCode) {
        return verificationCodeRepository.save(verificationCode);
    }

    @Override
    public void deleteVerificationCode(Long verificationCodeId) {
        verificationCodeRepository.deleteById(verificationCodeId);
    }

    @Override
    public Optional<VerificationCode> getVerificationCodeByUserId(Long userId) {
        return verificationCodeRepository.findVerificationCodeByUserId(userId);
    }

    @Override
    public Optional<VerificationCode> getVerificationCodeByVerificationCodeId(Long verificationCodeId) {
        return verificationCodeRepository.findById(verificationCodeId);
    }

    @Override
    public void deleteVerificationCodeByUserId(Long userId) {
        verificationCodeRepository.deleteVerificationCodeByUserId(userId);
    }
}
