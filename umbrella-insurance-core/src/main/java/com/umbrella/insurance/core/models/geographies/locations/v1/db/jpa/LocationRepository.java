package com.umbrella.insurance.core.models.geographies.locations.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> getLocationByLocationName(String locationName);
    void deleteLocationByLocationName(String locationName);

}
