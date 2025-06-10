package com.umbrella.insurance.core.models.periods.periodTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PeriodType;

import java.util.List;
import java.util.Optional;

public interface PeriodTypeService {
    PeriodType savePeriodType(PeriodType periodType);
    List<PeriodType> getPeriodTypes();
    PeriodType updatePeriodType(PeriodType periodType);
    void deletePeriodType(Long periodTypeId);
    Optional<PeriodType> getPeriodTypeByPeriodTypeName(String periodTypeName);
    Optional<PeriodType> getPeriodTypeById(Long periodTypeId);
    void deletePeriodTypeByPeriodTypeName(String periodTypeName);
}
