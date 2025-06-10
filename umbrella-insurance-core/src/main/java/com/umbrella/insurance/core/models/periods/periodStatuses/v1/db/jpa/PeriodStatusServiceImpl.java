package com.umbrella.insurance.core.models.periods.periodStatuses.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.umbrella.insurance.core.models.entities.PeriodStatus;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PeriodStatusServiceImpl implements PeriodStatusService {

    @Autowired
    PeriodStatusRepository periodStatusRepository;

    @Override
    public PeriodStatus savePeriodStatus(PeriodStatus periodStatus) {
        return periodStatusRepository.save(periodStatus);
    }

    @Override
    public List<PeriodStatus> getPeriodStatuses() {
        return periodStatusRepository.findAll();
    }

    @Override
    public PeriodStatus updatePeriodStatus(PeriodStatus periodStatus) {
        return periodStatusRepository.save(periodStatus);
    }

    @Override
    public void deletePeriodStatus(Long periodStatusId) {
        periodStatusRepository.deleteById(periodStatusId);
    }

    @Override
    public Optional<PeriodStatus> getPeriodStatus(Long periodStatusId) {
        return periodStatusRepository.findById(periodStatusId);
    }

    @Override
    public Optional<PeriodStatus> getPeriodStatus(String periodStatusName) {
        return periodStatusRepository.findPeriodStatusByPeriodStatusName(periodStatusName);
    }

    @Override
    public void deletePeriodStatusByPeriodStatusName(String periodStatusName) {
        periodStatusRepository.deletePeriodStatusByPeriodStatusName(periodStatusName);
    }
}
