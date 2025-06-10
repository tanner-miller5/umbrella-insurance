package com.umbrella.insurance.core.models.periods.periodTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PeriodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodTypeRepository extends JpaRepository<PeriodType, Long> {
    Optional<PeriodType> findPeriodTypeByPeriodTypeName(String periodTypeName);
    void deletePeriodTypeByPeriodTypeName(String periodTypeName);
}
