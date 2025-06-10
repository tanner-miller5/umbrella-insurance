package com.umbrella.insurance.core.models.geographies.cities.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.City;

import java.util.List;
import java.util.Optional;

public interface CityService {
    City saveCity(City cityRecord);
    List<City> getCities();
    City updateCity(City city);
    void deleteCity(Long cityId);
    Optional<City> getCityById(Long cityId);
    Optional<City> getCityByCityName(String cityName);
    void deleteCityByCityName(String cityName);
}
