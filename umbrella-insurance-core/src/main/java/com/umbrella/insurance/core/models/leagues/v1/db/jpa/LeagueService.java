package com.umbrella.insurance.core.models.leagues.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.League;

import java.util.List;
import java.util.Optional;

public interface LeagueService {
    League saveLeague(League league);
    List<League> getLeagues();
    League updateLeague(League league);
    void deleteLeague(Long leagueId);
    Optional<League> getLeague(Long leagueId);
    Optional<League> getLeagueByLeagueNameAndGameTypeId(
            String leagueName, Long gameTypeId);
    void deleteLeagueByLeagueNameAndGameTypeId(String leagueName, Long gameTypeId);
}
