package com.umbrella.insurance.core.models.stats.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Stat;

import java.util.List;
import java.util.Optional;

public interface StatService {
    Stat saveStat(Stat stat);
    List<Stat> getStats();
    Stat updateStat(Stat stat);
    void deleteStat(Long statId);
    Optional<Stat> getStatByStatName(String statName);
    void deleteStatByStatName(String statName);
    Optional<Stat> getStatById(Long statId);
}
