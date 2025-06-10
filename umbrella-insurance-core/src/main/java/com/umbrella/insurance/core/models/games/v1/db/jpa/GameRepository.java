package com.umbrella.insurance.core.models.games.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findGameByGameName(String gameStatus);
    void deleteByGameName(String gameStatus);
}
