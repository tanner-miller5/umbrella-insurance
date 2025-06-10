package com.umbrella.insurance.core.models.stats.teamStats.footballTeamStats.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.FootballTeamStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FootballTeamStatsServiceImpl implements FootballTeamStatsService {
    @Autowired
    FootballTeamStatsRepository footballTeamStatsRepository;

    @Override
    public FootballTeamStats saveFootballTeamStats(FootballTeamStats footballTeamStats) {
        return footballTeamStatsRepository.save(footballTeamStats);
    }

    @Override
    public List<FootballTeamStats> getFootballTeamStats() {
        return footballTeamStatsRepository.findAll();
    }

    @Override
    public FootballTeamStats updateFootballTeamStats(FootballTeamStats footballTeamStats) {
        return footballTeamStatsRepository.save(footballTeamStats);
    }

    @Override
    public void deleteFootballTeamStats(Long footballTeamStatsId) {
        footballTeamStatsRepository.deleteById(footballTeamStatsId);
    }

    @Override
    public Optional<FootballTeamStats> getFootballTeamStatsByGameIdAndTeamId(Long gameId, Long teamId) {
        return footballTeamStatsRepository.findByGameIdAndTeamId(gameId, teamId);
    }

    @Override
    public Optional<FootballTeamStats> getFootballTeamStatsByFootballTeamStatsId(Long footballTeamStatsId) {
        return footballTeamStatsRepository.findById(footballTeamStatsId);
    }

    @Override
    public void deleteFootballTeamStatsByGameIdAndTeamId(Long gameId, Long teamId) {
        footballTeamStatsRepository.deleteByGameIdAndTeamId(gameId, teamId);
    }
}
