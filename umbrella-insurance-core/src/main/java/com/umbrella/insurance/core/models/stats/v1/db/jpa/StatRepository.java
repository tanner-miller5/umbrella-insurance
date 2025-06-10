package com.umbrella.insurance.core.models.stats.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {
    Optional<Stat> getStatByStatName(String statName);
    void deleteStatByStatName(String statName);
}
