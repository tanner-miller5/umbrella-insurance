package com.umbrella.insurance.core.models.users.passwords.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
    Optional<Password> getPasswordByUserId(Long userId);
    void deletePasswordByUserId(Long userId);
}
