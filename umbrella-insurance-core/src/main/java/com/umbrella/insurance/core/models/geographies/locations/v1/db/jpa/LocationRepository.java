package com.umbrella.insurance.core.models.geographies.locations.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> getLocationByLocationName(String locationName);
    void deleteLocationByLocationName(String locationName);
    @Query(value = "SELECT l.* FROM locations l JOIN states s ON l.state_id = s.state_id WHERE s.state_name = :state ORDER BY l.location_name ASC", nativeQuery = true)
    List<Location> getLocationsByState(@Param("state") String state);
}
