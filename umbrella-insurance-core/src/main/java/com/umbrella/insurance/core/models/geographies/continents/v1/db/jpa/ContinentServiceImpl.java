package com.umbrella.insurance.core.models.geographies.continents.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Continent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContinentServiceImpl implements ContinentService {
    @Autowired
    ContinentRepository continentRepository;

    @Override
    public Continent saveContinent(Continent continent) {
        return continentRepository.save(continent);
    }

    @Override
    public List<Continent> getContinents() {
        return continentRepository.findAll();
    }

    @Override
    public Continent updateContinent(Continent continent) {
        return continentRepository.save(continent);
    }

    @Override
    public void deleteContinent(Long continentId) {
        continentRepository.deleteById(continentId);
    }

    @Override
    public Optional<Continent> getContinentByContinentName(String continentName) {
        return continentRepository.findByContinentName(continentName);
    }

    @Override
    public Optional<Continent> getContinentByContinentId(Long continentId) {
        return continentRepository.findById(continentId);
    }

    @Override
    public void deleteContinentByContinentName(String continentName) {
        continentRepository.deleteContinentByContinentName(continentName);
    }
}
