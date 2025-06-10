package com.umbrella.insurance.core.models.tournaments.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentService {
    Tournament saveTournament(Tournament tournament);
    List<Tournament> getTournaments();
    Tournament updateTournament(Tournament tournament);
    void deleteTournament(Long tournamentId);
    Optional<Tournament> getTournamentByTournamentName(String tournamentName);
    Optional<Tournament> getTournamentById(Long tournamentId);
    void deleteTournamentByTournamentName(String tournamentName);
}
