package com.umbrella.insurance.core.models.geographies.countries.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCountryName(String countryName);
    void deleteByCountryName(String countryName);
}
