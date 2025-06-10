package com.umbrella.insurance.core.models.geographies.cities.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umbrella.insurance.core.models.entities.City;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CityServiceImpl implements CityService {
    @Autowired
    CityRepository cityRepository;

    @Override
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public City updateCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public void deleteCity(Long cityId) {
        cityRepository.deleteById(cityId);
    }

    @Override
    public Optional<City> getCityById(Long cityId) {
        return cityRepository.findById(cityId);
    }

    @Override
    public Optional<City> getCityByCityName(String cityName) {
        return cityRepository.getCityByCityName(cityName);
    }

    @Override
    public void deleteCityByCityName(String cityName) {
        cityRepository.deleteByCityName(cityName);
    }


}
