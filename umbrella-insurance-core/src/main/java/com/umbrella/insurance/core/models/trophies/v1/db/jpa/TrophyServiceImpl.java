package com.umbrella.insurance.core.models.trophies.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Trophy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrophyServiceImpl implements TrophyService {
    @Autowired
    TrophyRepository trophyRepository;

    @Override
    public Trophy saveTrophy(Trophy trophy) {
        return trophyRepository.save(trophy);
    }

    @Override
    public List<Trophy> getTrophies() {
        return trophyRepository.findAll();
    }

    @Override
    public Trophy updateTrophy(Trophy trophy) {
        return trophyRepository.save(trophy);
    }

    @Override
    public void deleteTrophy(Long trophyId) {
        trophyRepository.deleteById(trophyId);
    }

    @Override
    public Optional<Trophy> getTrophyByTrophyName(String trophyName) {
        return trophyRepository.findTrophyByTrophyName(trophyName);
    }

    @Override
    public void deleteTrophyByTrophyName(String trophyName) {
        trophyRepository.deleteTrophyByTrophyName(trophyName);
    }

    @Override
    public Optional<Trophy> getTrophyById(Long trophyId) {
        return trophyRepository.findById(trophyId);
    }
}
