package com.umbrella.insurance.core.models.tournaments.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Optional<Tournament> findByTournamentName(String tournamentName);
    void deleteTournamentByTournamentName(String tournamentName);
}
