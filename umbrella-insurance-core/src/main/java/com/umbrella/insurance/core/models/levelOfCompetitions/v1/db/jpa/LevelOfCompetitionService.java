package com.umbrella.insurance.core.models.levelOfCompetitions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.LevelOfCompetition;

import java.util.List;
import java.util.Optional;

public interface LevelOfCompetitionService {
    LevelOfCompetition saveLevelOfCompetition(LevelOfCompetition levelOfCompetition);
    List<LevelOfCompetition> getLevelOfCompetitions();
    LevelOfCompetition updateLevelOfCompetition(LevelOfCompetition levelOfCompetition);
    void deleteLevelOfCompetition(Long levelOfCompetitionId);
    Optional<LevelOfCompetition> getLevelOfCompetitionById(Long levelOfCompetitionId);
    Optional<LevelOfCompetition> getLevelOfCompetitionByLevelOfCompetitionName(String levelOfCompetitionName);
    void deleteLevelOfCompetitionByLevelOfCompetitionName(String levelOfCompetitionName);
}
