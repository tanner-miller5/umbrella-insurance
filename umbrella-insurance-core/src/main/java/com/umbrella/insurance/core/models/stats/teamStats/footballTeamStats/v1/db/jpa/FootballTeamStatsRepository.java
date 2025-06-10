package com.umbrella.insurance.core.models.stats.teamStats.footballTeamStats.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.FootballTeamStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FootballTeamStatsRepository extends JpaRepository<FootballTeamStats, Long> {
    Optional<FootballTeamStats> findByGameIdAndTeamId(long gameId, long teamId);
    void deleteByGameIdAndTeamId(long gameId, long teamId);
}
