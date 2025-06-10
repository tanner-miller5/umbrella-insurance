package com.umbrella.insurance.core.models.users.totps.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TotpServiceImpl implements TotpService {

    @Autowired
    TotpRepository totpRepository;

    @Override
    public Totp saveTotp(Totp totp) {
        return totpRepository.save(totp);
    }

    @Override
    public List<Totp> getTotps() {
        return totpRepository.findAll();
    }

    @Override
    public Totp updateTotp(Totp totp) {
        return totpRepository.save(totp);
    }

    @Override
    public void deleteTotp(Long totpId) {
        totpRepository.deleteById(totpId);
    }

    @Override
    public Optional<Totp> getTotpByUserId(Long userId) {
        return totpRepository.findTotpByUserId(userId);
    }

    @Override
    public void deleteTotpByUserId(Long userId) {

    }

    @Override
    public Optional<Totp> getTotpByTotpId(Long totpId) {
        return totpRepository.findById(totpId);
    }
}
