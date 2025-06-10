package com.umbrella.insurance.core.models.leagues.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    Optional<League> findByLeagueNameAndGameTypeId(String leagueName, Long gameTypeId);
    void deleteByLeagueNameAndGameTypeId(String leagueName, Long gameTypeId);
}
