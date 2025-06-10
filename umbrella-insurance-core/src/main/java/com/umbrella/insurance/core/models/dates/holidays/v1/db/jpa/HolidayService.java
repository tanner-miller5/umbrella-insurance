package com.umbrella.insurance.core.models.dates.holidays.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Holiday;
import java.util.List;
import java.util.Optional;

public interface HolidayService {
    Holiday saveHoliday(Holiday holiday);
    List<Holiday> getHolidays();
    Holiday updateHoliday(Holiday holiday);
    void deleteHoliday(Long holidayId);
    Optional<Holiday> getHolidayByHolidayName(String holidayName);
    void deleteHolidayByHolidayName(String holidayName);
    Optional<Holiday> getHolidayById(Long holidayId);
}
