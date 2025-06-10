package com.umbrella.insurance.core.models.geographies.countries.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    Country saveCountry(Country country);
    List<Country> getCountries();
    Country updateCountry(Country country);
    void deleteCountry(Long countryId);
    Optional<Country> findByCountryId(Long countryId);
    Optional<Country> findByCountryName(String name);
    void deleteCountryByCountryName(String countryName);

}
