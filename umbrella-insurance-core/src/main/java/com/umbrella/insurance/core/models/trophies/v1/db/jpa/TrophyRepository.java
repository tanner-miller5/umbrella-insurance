package com.umbrella.insurance.core.models.trophies.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrophyRepository extends JpaRepository<Trophy, Long> {
    Optional<Trophy> findTrophyByTrophyName(String trophyName);
    void deleteTrophyByTrophyName(String trophyName);
}
