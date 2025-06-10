package com.umbrella.insurance.core.models.periods.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeriodServiceImpl implements PeriodService {
    @Autowired
    PeriodRepository periodRepository;

    @Override
    public Period savePeriod(Period period) {
        return periodRepository.save(period);
    }

    @Override
    public List<Period> getPeriods() {
        return periodRepository.findAll();
    }

    @Override
    public Period updatePeriod(Period period) {
        return periodRepository.save(period);
    }

    @Override
    public void deletePeriod(Long periodId) {
        periodRepository.deleteById(periodId);
    }

    @Override
    public Optional<Period> getPeriodById(Long periodId) {
        return periodRepository.findById(periodId);
    }

    @Override
    public Optional<Period> getPeriodByGameIdAndPeriodNumber(Long gameId, Long periodNumber) {
        return periodRepository.findByGameIdAndPeriodNumber(gameId, periodNumber);
    }

    @Override
    public void deletePeriodByGameIdAndPeriodNumber(Long gameId, Long periodNumber) {
        periodRepository.deleteByGameIdAndPeriodNumber(gameId, periodNumber);
    }
}
