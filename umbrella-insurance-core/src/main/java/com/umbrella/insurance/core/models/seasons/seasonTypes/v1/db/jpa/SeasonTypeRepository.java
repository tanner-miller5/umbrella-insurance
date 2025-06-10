package com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.SeasonType;

import java.util.Optional;

@Repository
public interface SeasonTypeRepository extends JpaRepository<SeasonType, Long> {
    Optional<SeasonType> findBySeasonTypeName(String seasonTypeName);
    void deleteBySeasonTypeName(String seasonTypeName);
}
