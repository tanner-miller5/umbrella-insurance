package com.umbrella.insurance.core.models.users.passwords.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Password;

import java.util.List;
import java.util.Optional;

public interface PasswordService {
    Password savePassword(Password password);
    List<Password> getPasswords();
    Password updatePassword(Password password);
    void deletePassword(Long passwordId);
    Optional<Password> getPasswordByUserId(Long userId);
    Optional<Password> getPasswordByPasswordId(Long passwordId);
    void deletePasswordByUserId(Long userId);
}
