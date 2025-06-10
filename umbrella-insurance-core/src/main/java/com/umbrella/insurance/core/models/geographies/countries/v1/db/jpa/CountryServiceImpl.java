package com.umbrella.insurance.core.models.geographies.countries.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {
    @Autowired
    CountryRepository countryRepository;

    @Override
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country updateCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public void deleteCountry(Long countryId) {
        countryRepository.deleteById(countryId);
    }

    @Override
    public Optional<Country> findByCountryId(Long continentId) {
        return countryRepository.findById(continentId);
    }

    @Override
    public Optional<Country> findByCountryName(String name) {
        return countryRepository.findByCountryName(name);
    }

    @Override
    public void deleteCountryByCountryName(String countryName) {
        countryRepository.deleteByCountryName(countryName);
    }

}
