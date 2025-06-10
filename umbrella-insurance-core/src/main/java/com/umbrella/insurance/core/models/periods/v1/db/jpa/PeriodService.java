package com.umbrella.insurance.core.models.periods.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Period;

import java.util.List;
import java.util.Optional;

public interface PeriodService {
    Period savePeriod(Period period);
    List<Period> getPeriods();
    Period updatePeriod(Period periodRecord);
    void deletePeriod(Long periodId);
    Optional<Period> getPeriodById(Long periodId);
    Optional<Period> getPeriodByGameIdAndPeriodNumber(Long gameId, Long periodNumber);
    void deletePeriodByGameIdAndPeriodNumber(Long gameId, Long periodNumber);
}
