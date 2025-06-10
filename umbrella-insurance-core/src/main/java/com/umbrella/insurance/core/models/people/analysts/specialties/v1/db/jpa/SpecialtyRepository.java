package com.umbrella.insurance.core.models.people.analysts.specialties.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    Optional<Specialty> getSpecialtyBySpecialtyName(String specialtyName);
    void deleteBySpecialtyName(String SpecialtyName);
}
