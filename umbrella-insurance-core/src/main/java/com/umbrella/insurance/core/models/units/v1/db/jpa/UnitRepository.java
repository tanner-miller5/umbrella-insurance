package com.umbrella.insurance.core.models.units.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findByUnitName(String unitName);
    void deleteByUnitName(String unitName);
}
