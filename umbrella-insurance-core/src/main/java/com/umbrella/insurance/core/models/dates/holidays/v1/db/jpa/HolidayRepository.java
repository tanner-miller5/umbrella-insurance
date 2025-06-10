package com.umbrella.insurance.core.models.dates.holidays.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    Optional<Holiday> findByHolidayName(String holidayName);
    void deleteHolidayByHolidayName(String holidayName);
}
