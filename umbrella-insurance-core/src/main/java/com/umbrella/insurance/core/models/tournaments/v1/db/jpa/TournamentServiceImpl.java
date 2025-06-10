package com.umbrella.insurance.core.models.tournaments.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TournamentServiceImpl implements TournamentService {
    @Autowired
    TournamentRepository tournamentRepository;

    @Override
    public Tournament saveTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @Override
    public List<Tournament> getTournaments() {
        return tournamentRepository.findAll();
    }

    @Override
    public Tournament updateTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @Override
    public void deleteTournament(Long tournamentId) {
        tournamentRepository.deleteById(tournamentId);
    }

    @Override
    public Optional<Tournament> getTournamentByTournamentName(String tournamentName) {
        return tournamentRepository.findByTournamentName(tournamentName);
    }

    @Override
    public Optional<Tournament> getTournamentById(Long tournamentId) {
        return tournamentRepository.findById(tournamentId);
    }

    @Override
    public void deleteTournamentByTournamentName(String tournamentName) {
        tournamentRepository.deleteTournamentByTournamentName(tournamentName);
    }
}
