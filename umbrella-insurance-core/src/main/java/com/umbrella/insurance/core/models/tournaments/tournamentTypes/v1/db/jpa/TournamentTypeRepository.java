package com.umbrella.insurance.core.models.tournaments.tournamentTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TournamentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TournamentTypeRepository extends JpaRepository<TournamentType, Long> {
    Optional<TournamentType> findTournamentTypeByTournamentTypeName(String tournamentTypeName);
    void deleteByTournamentTypeName(String tournamentTypeName);
}
