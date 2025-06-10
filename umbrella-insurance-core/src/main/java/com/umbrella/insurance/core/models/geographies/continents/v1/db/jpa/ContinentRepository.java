package com.umbrella.insurance.core.models.geographies.continents.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContinentRepository extends JpaRepository<Continent, Long> {
    Optional<Continent> findByContinentName(String name);
    void deleteContinentByContinentName(String continentName);
}
