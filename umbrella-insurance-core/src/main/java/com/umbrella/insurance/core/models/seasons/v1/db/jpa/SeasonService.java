package com.umbrella.insurance.core.models.seasons.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Season;

import java.util.List;
import java.util.Optional;

public interface SeasonService {
    Season saveSeason(Season season);
    List<Season> getSeasons();
    Season updateSeason(Season season);
    void deleteSeason(Long seasonId);
    Optional<Season> getSeasonBySeasonName(String seasonName);
    void deleteSeasonBySeasonName(String seasonName);
    Optional<Season> getSeasonById(Long seasonId);
}
