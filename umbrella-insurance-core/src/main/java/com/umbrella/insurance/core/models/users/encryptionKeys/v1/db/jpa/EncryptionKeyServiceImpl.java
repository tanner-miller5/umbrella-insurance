package com.umbrella.insurance.core.models.users.encryptionKeys.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.EncryptionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EncryptionKeyServiceImpl implements EncryptionKeyService {
    @Autowired
    EncryptionKeyRepository encryptionKeyRepository;

    @Override
    public EncryptionKey saveEncryptionKey(EncryptionKey encryptionKey) {
        return encryptionKeyRepository.save(encryptionKey);
    }

    @Override
    public List<EncryptionKey> getEncryptionKeys() {
        return encryptionKeyRepository.findAll();
    }

    @Override
    public EncryptionKey updateEncryptionKey(EncryptionKey encryptionKey) {
        return encryptionKeyRepository.save(encryptionKey);
    }

    @Override
    public void deleteEncryptionKey(Long encryptionKeyId) {
        encryptionKeyRepository.deleteById(encryptionKeyId);
    }

    @Override
    public Optional<EncryptionKey> getEncryptionKeyByUserId(Long userId) {
        return encryptionKeyRepository.findEncryptionKeyByUserId(userId);
    }

    @Override
    public void deleteEncryptionKeyByUserId(Long userId) {
        encryptionKeyRepository.deleteEncryptionKeyByUserId(userId);
    }

    @Override
    public Optional<EncryptionKey> getEncryptionKeyByEncryptionKeyId(Long encryptionKeyId) {
        return encryptionKeyRepository.findById(encryptionKeyId);
    }
}
