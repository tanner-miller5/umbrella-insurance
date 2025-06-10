package com.umbrella.insurance.core.models.units.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UnitServiceImpl implements UnitService {
    @Autowired
    UnitRepository unitRepository;

    @Override
    public Unit saveUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    @Override
    public List<Unit> getUnits() {
        return unitRepository.findAll();
    }

    @Override
    public Unit updateUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    @Override
    public void deleteUnit(Long unitId) {
        unitRepository.deleteById(unitId);
    }

    @Override
    public Optional<Unit> getUnitByUnitName(String unitName) {
        return unitRepository.findByUnitName(unitName);
    }

    @Override
    public Optional<Unit> getUnitByUnitId(Long unitId) {
        return unitRepository.findById(unitId);
    }

    @Override
    public void deleteUnitByUnitName(String unitName) {
        unitRepository.deleteByUnitName(unitName);
    }
}
