package com.umbrella.insurance.core.models.schedules.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Schedule;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    Schedule saveSchedule(Schedule schedule);
    List<Schedule> getSchedules();
    Schedule updateSchedule(Schedule schedule);
    void deleteSchedule(Long scheduleId);
    Optional<Schedule> getScheduleById(Long scheduleId);
    Optional<Schedule> getScheduleByGameId(Long gameId);
    void deleteScheduleByGameId(Long gameId);
}
