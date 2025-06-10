package com.umbrella.insurance.core.models.schedules.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.Schedule;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> getScheduleByGameId(Long gameId);
    void deleteScheduleByGameId(Long gameId);
}
