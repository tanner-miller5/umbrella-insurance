package com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Specialty;

import java.util.List;
import java.util.Optional;

public interface SpecialtyService {
    Specialty saveSpecialty(Specialty specialty);
    List<Specialty> getSpecialties();
    Specialty updateSpecialty(Specialty specialty);
    void deleteSpecialty(Long specialtyId);
    Optional<Specialty> getSpecialtyBySpecialtyName(String SpecialtyName);
    Optional<Specialty> getSpecialtyById(Long id);
    void deleteBySpecialtyName(String SpecialtyName);
}
