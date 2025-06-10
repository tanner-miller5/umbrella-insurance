package com.umbrella.insurance.core.models.users.encryptionKeys.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.EncryptionKey;

import java.util.List;
import java.util.Optional;

public interface EncryptionKeyService {
    EncryptionKey saveEncryptionKey(EncryptionKey encryptionKey);
    List<EncryptionKey> getEncryptionKeys();
    EncryptionKey updateEncryptionKey(EncryptionKey encryptionKey);
    void deleteEncryptionKey(Long encryptionKeyId);
    Optional<EncryptionKey> getEncryptionKeyByUserId(Long userId);
    void deleteEncryptionKeyByUserId(Long userId);
    Optional<EncryptionKey> getEncryptionKeyByEncryptionKeyId(Long encryptionKeyId);
}
