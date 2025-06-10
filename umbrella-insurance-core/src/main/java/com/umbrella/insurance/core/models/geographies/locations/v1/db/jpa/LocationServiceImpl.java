package com.umbrella.insurance.core.models.geographies.locations.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {
    @Autowired
    LocationRepository locationRepository;

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location updateLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }

    @Override
    public Optional<Location> getLocationByLocationName(String locationName) {
        return locationRepository.getLocationByLocationName(locationName);
    }

    @Override
    public void deleteByLocationName(String locationName) {
        locationRepository.deleteLocationByLocationName(locationName);
    }

    @Override
    public Optional<Location> getLocationByLocationId(Long locationId) {
        return locationRepository.findById(locationId);
    }

    @Override
    public void deleteByLocationId(Long locationId) {
        locationRepository.deleteById(locationId);
    }
}
