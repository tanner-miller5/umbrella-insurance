package com.umbrella.insurance.core.models.geographies.cities.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> getCityByCityName(String cityName);
    Optional<City> getCityById(Long cityId);
    void deleteByCityName(String cityName);
}
