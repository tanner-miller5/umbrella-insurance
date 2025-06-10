package com.umbrella.insurance.core.models.geographies.islands.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Island;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IslandRepository extends JpaRepository<Island, Long> {
    Optional<Island> findByIslandName(String islandName);
    void deleteIslandByIslandName(String islandName);
}
