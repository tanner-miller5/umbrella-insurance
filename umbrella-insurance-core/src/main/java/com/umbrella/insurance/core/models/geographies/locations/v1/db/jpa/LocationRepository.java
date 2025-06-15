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
    @Query(value = "SELECT * FROM locations l, cities c WHERE l.city_id = c.city_id AND l.state_id=:state",
            nativeQuery = true)
    List<Location> getLocationsByState(@Param("state") String state);
}
