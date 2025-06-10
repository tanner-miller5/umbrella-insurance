package com.umbrella.insurance.core.models.tournaments.tournamentTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TournamentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TournamentTypeServiceImpl implements TournamentTypeService {
    @Autowired
    TournamentTypeRepository tournamentTypeRepository;

    @Override
    public TournamentType saveTournamentType(TournamentType tournamentType) {
        return tournamentTypeRepository.save(tournamentType);
    }

    @Override
    public List<TournamentType> getTournamentTypes() {
        return tournamentTypeRepository.findAll();
    }

    @Override
    public TournamentType updateTournamentType(TournamentType tournamentType) {
        return tournamentTypeRepository.save(tournamentType);
    }

    @Override
    public void deleteTournamentType(Long tournamentTypeId) {
        tournamentTypeRepository.deleteById(tournamentTypeId);
    }

    @Override
    public Optional<TournamentType> findTournamentTypeByTournamentTypeName(String tournamentTypeName) {
        return tournamentTypeRepository.findTournamentTypeByTournamentTypeName(tournamentTypeName);
    }

    @Override
    public void deleteTournamentTypeByTournamentTypeName(String tournamentTypeName) {
        tournamentTypeRepository.deleteByTournamentTypeName(tournamentTypeName);
    }

    @Override
    public Optional<TournamentType> findTournamentTypeById(Long tournamentTypeId) {
        return tournamentTypeRepository.findById(tournamentTypeId);
    }
}
