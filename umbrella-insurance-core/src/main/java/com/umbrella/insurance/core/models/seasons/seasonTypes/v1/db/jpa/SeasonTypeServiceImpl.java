package com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.SeasonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SeasonTypeServiceImpl  implements SeasonTypeService {
    @Autowired
    SeasonTypeRepository seasonTypeRepository;

    @Override
    public SeasonType saveSeasonType(SeasonType seasonType) {
        return seasonTypeRepository.save(seasonType);
    }

    @Override
    public List<SeasonType> getSeasonTypes() {
        return seasonTypeRepository.findAll();
    }

    @Override
    public SeasonType updateSeasonType(SeasonType seasonType) {
        return seasonTypeRepository.save(seasonType);
    }

    @Override
    public void deleteSeasonType(Long seasonTypeId) {
        seasonTypeRepository.deleteById(seasonTypeId);
    }

    @Override
    public Optional<SeasonType> getSeasonTypeBySeasonTypeName(String seasonTypeName) {
        return seasonTypeRepository.findBySeasonTypeName(seasonTypeName);
    }

    @Override
    public void deleteSeasonTypeBySeasonTypeName(String seasonTypeName) {
        seasonTypeRepository.deleteBySeasonTypeName(seasonTypeName);
    }

    @Override
    public Optional<SeasonType> getSeasonTypeById(Long seasonTypeId) {
        return seasonTypeRepository.findById(seasonTypeId);
    }
}
