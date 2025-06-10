package com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.SeasonType;

import java.util.List;
import java.util.Optional;

public interface SeasonTypeService {
    SeasonType saveSeasonType(SeasonType seasonType);
    List<SeasonType> getSeasonTypes();
    SeasonType updateSeasonType(SeasonType seasonType);
    void deleteSeasonType(Long seasonTypeId);
    Optional<SeasonType> getSeasonTypeBySeasonTypeName(String seasonTypeName);
    void deleteSeasonTypeBySeasonTypeName(String seasonTypeName);
    Optional<SeasonType> getSeasonTypeById(Long seasonTypeId);
}
