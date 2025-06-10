package com.umbrella.insurance.core.models.equipment.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    EquipmentRepository equipmentRepository;

    @Override
    public Equipment saveEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public List<Equipment> getEquipments() {
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment updateEquipment(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public void deleteEquipment(Long equipmentId) {
        equipmentRepository.deleteById(equipmentId);
    }

    @Override
    public Optional<Equipment> getEquipmentById(Long equipmentId) {
        return equipmentRepository.findById(equipmentId);
    }

    @Override
    public Optional<Equipment> getEquipmentByEquipmentName(String equipmentName) {
        return equipmentRepository.findEquipmentByEquipmentName(equipmentName);
    }

    @Override
    public void deleteByEquipmentName(String equipmentName) {
        equipmentRepository.deleteByEquipmentName(equipmentName);
    }
}
