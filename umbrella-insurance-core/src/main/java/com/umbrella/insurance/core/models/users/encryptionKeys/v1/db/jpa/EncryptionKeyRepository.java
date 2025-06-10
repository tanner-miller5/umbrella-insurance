package com.umbrella.insurance.core.models.users.encryptionKeys.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.EncryptionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EncryptionKeyRepository extends JpaRepository<EncryptionKey, Long> {
    Optional<EncryptionKey> findEncryptionKeyByUserId(Long UserId);
    void deleteEncryptionKeyByUserId(Long UserId);
}
