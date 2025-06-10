package com.umbrella.insurance.core.models.weeks.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Week;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WeekServiceImpl implements WeekService {
    @Autowired
    WeekRepository weekRepository;

    @Override
    public Week saveWeek(Week week) {
        return weekRepository.save(week);
    }

    @Override
    public List<Week> getWeeks() {
        return weekRepository.findAll();
    }

    @Override
    public Week updateWeek(Week week) {
        return weekRepository.save(week);
    }

    @Override
    public void deleteWeek(Long weekId) {
        weekRepository.deleteById(weekId);
    }

    @Override
    public Optional<Week> getWeekByWeekTitle(String weekTitle) {
        return weekRepository.findWeekByWeekTitle(weekTitle);
    }

    @Override
    public void deleteWeekByWeekTitle(String weekTitle) {
        weekRepository.deleteWeekByWeekTitle(weekTitle);
    }

    @Override
    public Optional<Week> getWeekByWeekId(Long weekId) {
        return weekRepository.findById(weekId);
    }
}
