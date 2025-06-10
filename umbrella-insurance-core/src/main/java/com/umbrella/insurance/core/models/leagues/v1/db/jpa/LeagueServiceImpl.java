package com.umbrella.insurance.core.models.leagues.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.League;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LeagueServiceImpl implements LeagueService {
    @Autowired
    LeagueRepository leagueRepository;

    @Override
    public League saveLeague(League league) {
        return leagueRepository.save(league);
    }

    @Override
    public List<League> getLeagues() {
        return leagueRepository.findAll();
    }

    @Override
    public League updateLeague(League league) {
        return leagueRepository.save(league);
    }

    @Override
    public void deleteLeague(Long leagueId) {
        leagueRepository.deleteById(leagueId);
    }

    @Override
    public Optional<League> getLeague(Long leagueId) {
        return leagueRepository.findById(leagueId);
    }

    @Override
    public Optional<League> getLeagueByLeagueNameAndGameTypeId(String leagueName, Long gameTypeId) {
        return leagueRepository.findByLeagueNameAndGameTypeId(leagueName, gameTypeId);
    }

    @Override
    public void deleteLeagueByLeagueNameAndGameTypeId(String leagueName, Long gameTypeId) {
        leagueRepository.deleteByLeagueNameAndGameTypeId(leagueName, gameTypeId);
    }
}
