package com.umbrella.insurance.core.models.stats.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatServiceImpl implements StatService {
    @Autowired
    StatRepository statRepository;

    @Override
    public Stat saveStat(Stat stat) {
        return statRepository.save(stat);
    }

    @Override
    public List<Stat> getStats() {
        return statRepository.findAll();
    }

    @Override
    public Stat updateStat(Stat stat) {
        return statRepository.save(stat);
    }

    @Override
    public void deleteStat(Long statId) {
        statRepository.deleteById(statId);
    }

    @Override
    public Optional<Stat> getStatByStatName(String statName) {
        return statRepository.getStatByStatName(statName);
    }

    @Override
    public void deleteStatByStatName(String statName) {
        statRepository.deleteStatByStatName(statName);
    }

    @Override
    public Optional<Stat> getStatById(Long statId) {
        return statRepository.findById(statId);
    }
}
