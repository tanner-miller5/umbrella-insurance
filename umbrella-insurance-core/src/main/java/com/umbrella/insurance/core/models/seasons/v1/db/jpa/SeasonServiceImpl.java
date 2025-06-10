package com.umbrella.insurance.core.models.seasons.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SeasonServiceImpl implements SeasonService {
    @Autowired
    SeasonRepository seasonRepository;

    @Override
    public Season saveSeason(Season season) {
        return seasonRepository.save(season);
    }

    @Override
    public List<Season> getSeasons() {
        return seasonRepository.findAll();
    }

    @Override
    public Season updateSeason(Season season) {
        return seasonRepository.save(season);
    }

    @Override
    public void deleteSeason(Long seasonId) {
        seasonRepository.deleteById(seasonId);
    }

    @Override
    public Optional<Season> getSeasonBySeasonName(String seasonName) {
        return seasonRepository.findBySeasonName(seasonName);
    }

    @Override
    public void deleteSeasonBySeasonName(String seasonName) {
        seasonRepository.deleteBySeasonName(seasonName);
    }

    @Override
    public Optional<Season> getSeasonById(Long seasonId) {
        return seasonRepository.findById(seasonId);
    }
}
