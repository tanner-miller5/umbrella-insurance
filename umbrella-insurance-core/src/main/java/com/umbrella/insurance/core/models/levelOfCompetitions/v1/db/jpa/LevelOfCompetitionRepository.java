package com.umbrella.insurance.core.models.levelOfCompetitions.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.LevelOfCompetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LevelOfCompetitionRepository extends JpaRepository<LevelOfCompetition, Long> {
    Optional<LevelOfCompetition> findByLevelOfCompetitionName(String levelOfCompetitionName);
    void deleteByLevelOfCompetitionName(String levelOfCompetitionName);
}
