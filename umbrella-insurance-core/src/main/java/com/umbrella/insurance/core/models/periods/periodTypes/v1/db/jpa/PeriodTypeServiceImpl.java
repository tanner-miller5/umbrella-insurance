package com.umbrella.insurance.core.models.periods.periodTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeriodTypeServiceImpl implements PeriodTypeService {
    @Autowired
    PeriodTypeRepository periodTypeRepository;

    @Override
    public PeriodType savePeriodType(PeriodType periodType) {
        return periodTypeRepository.save(periodType);
    }

    @Override
    public List<PeriodType> getPeriodTypes() {
        return periodTypeRepository.findAll();
    }

    @Override
    public PeriodType updatePeriodType(PeriodType periodType) {
        return periodTypeRepository.save(periodType);
    }

    @Override
    public void deletePeriodType(Long periodTypeId) {
        periodTypeRepository.deleteById(periodTypeId);
    }

    @Override
    public Optional<PeriodType> getPeriodTypeByPeriodTypeName(String periodTypeName) {
        return periodTypeRepository.findPeriodTypeByPeriodTypeName(periodTypeName);
    }

    @Override
    public Optional<PeriodType> getPeriodTypeById(Long periodTypeId) {
        return periodTypeRepository.findById(periodTypeId);
    }

    @Override
    public void deletePeriodTypeByPeriodTypeName(String periodTypeName) {
        periodTypeRepository.deletePeriodTypeByPeriodTypeName(periodTypeName);
    }
}
