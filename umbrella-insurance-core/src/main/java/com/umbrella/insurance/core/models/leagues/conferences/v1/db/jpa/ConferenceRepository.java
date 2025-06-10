package com.umbrella.insurance.core.models.leagues.conferences.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConferenceRepository  extends JpaRepository<Conference, Long> {
    Optional<Conference> findByConferenceNameAndLeagueId(
            String conferenceName, Long leagueId);
    void deleteByConferenceNameAndLeagueId(String conferenceName, Long leagueId);
}
