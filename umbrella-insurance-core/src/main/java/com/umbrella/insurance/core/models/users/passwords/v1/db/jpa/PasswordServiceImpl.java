package com.umbrella.insurance.core.models.users.passwords.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    PasswordRepository passwordRepository;

    @Override
    public Password savePassword(Password password) {
        return passwordRepository.save(password);
    }

    @Override
    public List<Password> getPasswords() {
        return passwordRepository.findAll();
    }

    @Override
    public Password updatePassword(Password password) {
        return passwordRepository.save(password);
    }

    @Override
    public void deletePassword(Long passwordId) {
        passwordRepository.deleteById(passwordId);
    }

    @Override
    public Optional<Password> getPasswordByUserId(Long userId) {
        return passwordRepository.getPasswordByUserId(userId);
    }

    @Override
    public Optional<Password> getPasswordByPasswordId(Long passwordId) {
        return passwordRepository.findById(passwordId);
    }

    @Override
    public void deletePasswordByUserId(Long userId) {
        passwordRepository.deletePasswordByUserId(userId);
    }
}
