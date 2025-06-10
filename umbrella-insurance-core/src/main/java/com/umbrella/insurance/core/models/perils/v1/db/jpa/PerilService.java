package com.umbrella.insurance.core.models.perils.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Peril;

import java.util.List;
import java.util.Optional;

public interface PerilService {
    Peril savePeril(Peril peril);
    List<Peril> getPerils();
    Peril updatePeril(Peril peril);
    void deletePeril(Long perilId);
    Optional<Peril> getPeril(Long perilId);
    Optional<Peril> getPerilByPerilName(String perilName);
    void deletePerilByPerilName(String perilName);
}
