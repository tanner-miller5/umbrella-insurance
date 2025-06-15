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
    @Query(value = "SELECT l.* FROM locations l, cities c, states s WHERE l.city_id = c.city_id AND l.state_id=s.state_id AND s.state_name=:state",
            nativeQuery = true)
    List<Location> getLocationsByState(@Param("state") String state);
}
