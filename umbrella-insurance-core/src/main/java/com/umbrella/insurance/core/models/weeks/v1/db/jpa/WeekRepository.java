package com.umbrella.insurance.core.models.weeks.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {
    Optional<Week> findWeekByWeekTitle(String weekTitle);
    void deleteWeekByWeekTitle(String weekTitle);
}
