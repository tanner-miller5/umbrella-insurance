package com.umbrella.insurance.core.models.leagues.conferences.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Conference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    ConferenceRepository conferenceRepository;

    @Override
    public Conference saveConference(Conference conference) {
        return conferenceRepository.save(conference);
    }

    @Override
    public List<Conference> getConferences() {
        return conferenceRepository.findAll();
    }

    @Override
    public Conference updateConference(Conference conference) {
        return conferenceRepository.save(conference);
    }

    @Override
    public void deleteConference(Long conferenceId) {
        conferenceRepository.deleteById(conferenceId);
    }

    @Override
    public Optional<Conference> getConferenceById(Long conferenceId) {
        return conferenceRepository.findById(conferenceId);
    }

    @Override
    public Optional<Conference> getConferenceByNameAndLeagueId(String conferenceName, Long leagueId) {
        return conferenceRepository.findByConferenceNameAndLeagueId(conferenceName, leagueId);
    }

    @Override
    public void deleteConferenceByConferenceNameAndLeagueId(String conferenceName, Long leagueId) {
        conferenceRepository.deleteByConferenceNameAndLeagueId(conferenceName, leagueId);
    }
}
