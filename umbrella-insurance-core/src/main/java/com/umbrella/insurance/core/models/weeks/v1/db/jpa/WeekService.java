package com.umbrella.insurance.core.models.weeks.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Week;

import java.util.List;
import java.util.Optional;

public interface WeekService {
    Week saveWeek(Week week);
    List<Week> getWeeks();
    Week updateWeek(Week week);
    void deleteWeek(Long weekId);
    Optional<Week> getWeekByWeekTitle(String weekTitle);
    void deleteWeekByWeekTitle(String weekTitle);
    Optional<Week> getWeekByWeekId(Long weekId);
}
