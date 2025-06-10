package com.umbrella.insurance.core.models.seasons.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.Season;

import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
    Optional<Season> findBySeasonName(String seasonName);
    void deleteBySeasonName(String seasonName);
}
