package com.umbrella.insurance.core.models.equipment.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Equipment;

import java.util.List;
import java.util.Optional;

public interface EquipmentService {
    Equipment saveEquipment(Equipment equipment);
    List<Equipment> getEquipments();
    Equipment updateEquipment(Equipment equipment);
    void deleteEquipment(Long equipmentId);
    Optional<Equipment> getEquipmentById(Long equipmentId);
    Optional<Equipment> getEquipmentByEquipmentName(String equipmentName);
    void deleteByEquipmentName(String equipmentName);
}
