package com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Specialty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SpecialtyServiceImpl implements SpecialtyService {
    @Autowired
    SpecialtyRepository specialtyRepository;

    @Override
    public Specialty saveSpecialty(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public List<Specialty> getSpecialties() {
        return specialtyRepository.findAll();
    }

    @Override
    public Specialty updateSpecialty(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public void deleteSpecialty(Long specialtyId) {
        specialtyRepository.deleteById(specialtyId);
    }

    @Override
    public Optional<Specialty> getSpecialtyBySpecialtyName(String SpecialtyName) {
        return specialtyRepository.getSpecialtyBySpecialtyName(SpecialtyName);
    }

    @Override
    public Optional<Specialty> getSpecialtyById(Long id) {
        return specialtyRepository.findById(id);
    }

    @Override
    public void deleteBySpecialtyName(String SpecialtyName) {
        specialtyRepository.deleteBySpecialtyName(SpecialtyName);
    }
}
