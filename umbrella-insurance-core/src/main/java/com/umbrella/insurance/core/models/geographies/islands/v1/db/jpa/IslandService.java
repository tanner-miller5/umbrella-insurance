package com.umbrella.insurance.core.models.geographies.islands.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Island;

import java.util.List;
import java.util.Optional;

public interface IslandService {
    Island saveIsland(Island island);
    List<Island> getIslands();
    Island updateIsland(Island island);
    void deleteIsland(Long islandId);
    Optional<Island> getIsland(Long islandId);
    Optional<Island> getIslandByIslandName(String islandName);
    void deleteIslandByIslandName(String islandName);
}
