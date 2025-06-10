package com.umbrella.insurance.core.models.leagues.conferences.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Conference;
import java.util.List;
import java.util.Optional;

public interface ConferenceService {
    Conference saveConference(Conference conference);
    List<Conference> getConferences();
    Conference updateConference(Conference conference);
    void deleteConference(Long conferenceId);
    Optional<Conference> getConferenceById(Long conferenceId);
    Optional<Conference> getConferenceByNameAndLeagueId(String conferenceName, Long leagueId);
    void deleteConferenceByConferenceNameAndLeagueId(String conferenceName, Long leagueId);
}
