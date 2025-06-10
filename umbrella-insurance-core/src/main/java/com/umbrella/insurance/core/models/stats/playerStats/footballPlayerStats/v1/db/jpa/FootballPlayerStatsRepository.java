package com.umbrella.insurance.core.models.stats.playerStats.footballPlayerStats.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.FootballPlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FootballPlayerStatsRepository extends JpaRepository<FootballPlayerStats, Long> {
    Optional<FootballPlayerStats> findByGameIdAndPlayerId(Long gameId, Long playerId);
    void deleteByGameIdAndPlayerId(Long gameId, Long playerId);
}
