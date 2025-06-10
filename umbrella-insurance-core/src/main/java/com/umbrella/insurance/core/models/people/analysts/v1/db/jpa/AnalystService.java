package com.umbrella.insurance.core.models.people.analysts.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Analyst;

import java.util.List;
import java.util.Optional;

public interface AnalystService {
    Analyst saveAnalyst(Analyst analyst);
    List<Analyst> getAnalysts();
    Analyst updateAnalyst(Analyst analyst);
    void deleteAnalyst(Long analystId);
    Optional<Analyst> getAnalystById(Long analystId);
    Optional<Analyst> getAnalystByPersonId(Long personId);
    void deleteAnalystByPersonId(Long personId);
}
