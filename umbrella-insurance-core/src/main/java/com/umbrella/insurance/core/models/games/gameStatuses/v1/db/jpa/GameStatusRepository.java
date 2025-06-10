package com.umbrella.insurance.core.models.games.gameStatuses.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameStatusRepository extends JpaRepository<GameStatus, Long> {
    Optional<GameStatus> findByGameStatusName(String gameStatusName);
    void deleteByGameStatusName(String gameStatusName);
}
