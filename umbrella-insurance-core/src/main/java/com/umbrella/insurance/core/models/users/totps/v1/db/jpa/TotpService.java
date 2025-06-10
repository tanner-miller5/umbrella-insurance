package com.umbrella.insurance.core.models.users.totps.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Totp;

import java.util.List;
import java.util.Optional;

public interface TotpService {
    Totp saveTotp(Totp totp);
    List<Totp> getTotps();
    Totp updateTotp(Totp totp);
    void deleteTotp(Long totpId);
    Optional<Totp> getTotpByUserId(Long userId);
    void deleteTotpByUserId(Long userId);
    Optional<Totp> getTotpByTotpId(Long totpId);
}
