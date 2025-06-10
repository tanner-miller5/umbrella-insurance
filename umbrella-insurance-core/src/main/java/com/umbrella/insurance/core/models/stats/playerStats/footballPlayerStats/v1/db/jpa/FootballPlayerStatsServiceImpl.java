package com.umbrella.insurance.core.models.stats.playerStats.footballPlayerStats.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.FootballPlayerStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FootballPlayerStatsServiceImpl implements FootballPlayerStatsService {
    @Autowired
    FootballPlayerStatsRepository footballPlayerStatsRepository;

    @Override
    public FootballPlayerStats saveFootballPlayerStats(FootballPlayerStats footballPlayerStats) {
        return footballPlayerStatsRepository.save(footballPlayerStats);
    }

    @Override
    public List<FootballPlayerStats> getFootballPlayerStats() {
        return footballPlayerStatsRepository.findAll();
    }

    @Override
    public FootballPlayerStats updateFootballPlayerStats(FootballPlayerStats footballPlayerStats) {
        return footballPlayerStatsRepository.save(footballPlayerStats);
    }

    @Override
    public void deleteFootballPlayerStats(Long footballPlayerStatsId) {
        footballPlayerStatsRepository.deleteById(footballPlayerStatsId);
    }

    @Override
    public Optional<FootballPlayerStats> getFootballPlayerStatsByGameIdAndPlayerId(Long gameId, Long playerId) {
        return footballPlayerStatsRepository.findByGameIdAndPlayerId(gameId, playerId);
    }

    @Override
    public void deleteFootballPlayerStatsByGameIdAndPlayerId(Long gameId, Long playerId) {
        footballPlayerStatsRepository.deleteByGameIdAndPlayerId(gameId, playerId);
    }

    @Override
    public Optional<FootballPlayerStats> getFootballPlayerStatsById(Long playerStatsId) {
        return footballPlayerStatsRepository.findById(playerStatsId);
    }
}
