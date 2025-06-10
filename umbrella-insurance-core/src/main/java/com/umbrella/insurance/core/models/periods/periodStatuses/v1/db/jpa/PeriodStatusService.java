package com.umbrella.insurance.core.models.periods.periodStatuses.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PeriodStatus;

import java.util.List;
import java.util.Optional;

public interface PeriodStatusService {
    PeriodStatus savePeriodStatus(PeriodStatus periodStatus);
    List<PeriodStatus> getPeriodStatuses();
    PeriodStatus updatePeriodStatus(PeriodStatus periodStatus);
    void deletePeriodStatus(Long periodStatusId);
    Optional<PeriodStatus> getPeriodStatus(Long periodStatusId);
    Optional<PeriodStatus> getPeriodStatus(String periodStatusName);
    void deletePeriodStatusByPeriodStatusName(String periodStatusName);
}
