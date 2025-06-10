package com.umbrella.insurance.core.models.stats.playerStats.footballPlayerStats.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.FootballPlayerStats;

import java.util.List;
import java.util.Optional;

public interface FootballPlayerStatsService {
    FootballPlayerStats saveFootballPlayerStats(FootballPlayerStats footballPlayerStats);
    List<FootballPlayerStats> getFootballPlayerStats();
    FootballPlayerStats updateFootballPlayerStats(FootballPlayerStats footballPlayerStats);
    void deleteFootballPlayerStats(Long footballPlayerStatsId);
    Optional<FootballPlayerStats> getFootballPlayerStatsByGameIdAndPlayerId(Long gameId, Long playerId);
    void deleteFootballPlayerStatsByGameIdAndPlayerId(Long gameId, Long playerId);
    Optional<FootballPlayerStats> getFootballPlayerStatsById(Long playerStatsId);
}
