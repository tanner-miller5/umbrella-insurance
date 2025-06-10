package com.umbrella.insurance.core.models.levelOfCompetitions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.LevelOfCompetition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LevelOfCompetitionServiceImpl implements LevelOfCompetitionService {

    @Autowired
    LevelOfCompetitionRepository levelOfCompetitionRepository;

    @Override
    public LevelOfCompetition saveLevelOfCompetition(LevelOfCompetition levelOfCompetition) {
        return levelOfCompetitionRepository.save(levelOfCompetition);
    }

    @Override
    public List<LevelOfCompetition> getLevelOfCompetitions() {
        return levelOfCompetitionRepository.findAll();
    }

    @Override
    public LevelOfCompetition updateLevelOfCompetition(LevelOfCompetition levelOfCompetition) {
        return levelOfCompetitionRepository.save(levelOfCompetition);
    }

    @Override
    public void deleteLevelOfCompetition(Long levelOfCompetitionId) {
        levelOfCompetitionRepository.deleteById(levelOfCompetitionId);
    }

    @Override
    public Optional<LevelOfCompetition> getLevelOfCompetitionById(Long levelOfCompetitionId) {
        return levelOfCompetitionRepository.findById(levelOfCompetitionId);
    }

    @Override
    public Optional<LevelOfCompetition> getLevelOfCompetitionByLevelOfCompetitionName(String levelOfCompetitionName) {
        return levelOfCompetitionRepository.findByLevelOfCompetitionName(levelOfCompetitionName);
    }

    @Override
    public void deleteLevelOfCompetitionByLevelOfCompetitionName(String levelOfCompetitionName) {
        levelOfCompetitionRepository.deleteByLevelOfCompetitionName(levelOfCompetitionName);
    }
}
