package com.umbrella.insurance.core.models.geographies.locations.v1.db.jpa;

import java.util.List;
import java.util.Optional;

import com.umbrella.insurance.core.models.entities.Location;

public interface LocationService {
    Location saveLocation(Location location);
    List<Location> getLocations();
    Location updateLocation(Location location);
    void deleteLocation(Long locationId);
    Optional<Location> getLocationByLocationName(String locationName);
    void deleteByLocationName(String locationName);
    Optional<Location> getLocationByLocationId(Long locationId);
    void deleteByLocationId(Long locationId);
    List<Location> getLocationsByState(String state);
}
