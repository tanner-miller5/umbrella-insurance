package com.umbrella.insurance.core.models.trophies.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Trophy;

import java.util.List;
import java.util.Optional;

public interface TrophyService {
    Trophy saveTrophy(Trophy trophy);
    List<Trophy> getTrophies();
    Trophy updateTrophy(Trophy trophy);
    void deleteTrophy(Long trophyId);
    Optional<Trophy> getTrophyByTrophyName(String trophyName);
    void deleteTrophyByTrophyName(String trophyName);
    Optional<Trophy> getTrophyById(Long trophyId);
}
