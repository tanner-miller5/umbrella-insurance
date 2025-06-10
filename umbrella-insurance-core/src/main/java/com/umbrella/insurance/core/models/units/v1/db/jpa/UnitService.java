package com.umbrella.insurance.core.models.units.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Unit;

import java.util.List;
import java.util.Optional;

public interface UnitService {
    Unit saveUnit(Unit unit);
    List<Unit> getUnits();
    Unit updateUnit(Unit unit);
    void deleteUnit(Long unitId);
    Optional<Unit> getUnitByUnitName(String unitName);
    Optional<Unit> getUnitByUnitId(Long unitId);
    void deleteUnitByUnitName(String unitName);
}
