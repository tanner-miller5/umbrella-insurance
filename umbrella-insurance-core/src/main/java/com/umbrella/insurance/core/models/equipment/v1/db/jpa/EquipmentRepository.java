package com.umbrella.insurance.core.models.equipment.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findEquipmentByEquipmentName(String equipmentName);
    void deleteByEquipmentName(String equipmentName);
}
