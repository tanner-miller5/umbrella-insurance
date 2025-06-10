package com.umbrella.insurance.core.models.geographies.islands.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Island;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IslandServiceImpl implements IslandService {
    @Autowired
    IslandRepository islandRepository;

    @Override
    public Island saveIsland(Island island) {
        return islandRepository.save(island);
    }

    @Override
    public List<Island> getIslands() {
        return islandRepository.findAll();
    }

    @Override
    public Island updateIsland(Island island) {
        return islandRepository.save(island);
    }

    @Override
    public void deleteIsland(Long islandId) {
        islandRepository.deleteById(islandId);
    }

    @Override
    public Optional<Island> getIsland(Long islandId) {
        return islandRepository.findById(islandId);
    }

    @Override
    public Optional<Island> getIslandByIslandName(String islandName) {
        return islandRepository.findByIslandName(islandName);
    }

    @Override
    public void deleteIslandByIslandName(String islandName) {
        islandRepository.deleteIslandByIslandName(islandName);
    }
}
