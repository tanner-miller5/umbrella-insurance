package com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZipCodeRepository extends JpaRepository<ZipCode, Long> {
    Optional<ZipCode> findByZipCodeValue(String zipCodeValue);
    void deleteByZipCodeValue(String zipCodeValue);
}
