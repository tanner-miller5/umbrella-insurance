package com.umbrella.insurance.core.models.periods.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {
    Optional<Period> findByGameIdAndPeriodNumber(Long gameId, Long periodNumber);
    void deleteByGameIdAndPeriodNumber(Long gameId, Long periodNumber);
}
