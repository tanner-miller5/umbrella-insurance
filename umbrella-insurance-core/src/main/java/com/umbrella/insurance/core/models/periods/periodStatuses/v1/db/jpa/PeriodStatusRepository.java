package com.umbrella.insurance.core.models.periods.periodStatuses.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PeriodStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodStatusRepository extends JpaRepository<PeriodStatus, Long> {
    Optional<PeriodStatus> findPeriodStatusByPeriodStatusName(String periodStatusName);
    void deletePeriodStatusByPeriodStatusName(String periodStatusName);
}
