package com.umbrella.insurance.core.models.stats.teamStats.footballTeamStats.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.FootballTeamStats;
import java.util.List;
import java.util.Optional;

public interface FootballTeamStatsService {
    FootballTeamStats saveFootballTeamStats(FootballTeamStats footballTeamStats);
    List<FootballTeamStats> getFootballTeamStats();
    FootballTeamStats updateFootballTeamStats(FootballTeamStats footballTeamStats);
    void deleteFootballTeamStats(Long footballTeamStatsId);
    Optional<FootballTeamStats> getFootballTeamStatsByGameIdAndTeamId(Long gameId,Long teamId);
    Optional<FootballTeamStats> getFootballTeamStatsByFootballTeamStatsId(Long footballTeamStatsId);
    void deleteFootballTeamStatsByGameIdAndTeamId(Long gameId,Long teamId);
}
