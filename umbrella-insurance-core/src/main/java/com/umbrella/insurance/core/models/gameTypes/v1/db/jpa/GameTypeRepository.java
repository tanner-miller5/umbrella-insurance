package com.umbrella.insurance.core.models.gameTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameTypeRepository extends JpaRepository<GameType, Long> {
    Optional<GameType> findByGameTypeName(String name);
    void deleteByGameTypeName(String gameTypeName);
}
