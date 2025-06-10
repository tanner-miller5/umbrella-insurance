package com.umbrella.insurance.core.models.tournaments.tournamentTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TournamentType;

import java.util.List;
import java.util.Optional;

public interface TournamentTypeService {
    TournamentType saveTournamentType(TournamentType tournamentType);
    List<TournamentType> getTournamentTypes();
    TournamentType updateTournamentType(TournamentType tournamentType);
    void deleteTournamentType(Long tournamentTypeId);
    Optional<TournamentType> findTournamentTypeByTournamentTypeName(String tournamentTypeName);
    void deleteTournamentTypeByTournamentTypeName(String tournamentTypeName);
    Optional<TournamentType> findTournamentTypeById(Long tournamentTypeId);
}
