package com.umbrella.insurance.core.models.geographies.continents.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Continent;

import java.util.List;
import java.util.Optional;

public interface ContinentService {
    Continent saveContinent(Continent continent);
    List<Continent> getContinents();
    Continent updateContinent(Continent continent);
    void deleteContinent(Long continentId);
    Optional<Continent> getContinentByContinentName(String continentName);
    Optional<Continent> getContinentByContinentId(Long continentId);
    void deleteContinentByContinentName(String continentName);
}
