package com.umbrella.insurance.core.models.games.gameStatuses.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.GameStatus;

import java.util.List;
import java.util.Optional;

public interface GameStatusService {
    GameStatus saveGameStatus(GameStatus gameStatus);
    List<GameStatus> getGameStatuses();
    GameStatus updateGameStatus(GameStatus gameStatus);
    void deleteGameStatus(Long gameStatusId);
    Optional<GameStatus> getGameStatus(Long gameStatusId);
    Optional<GameStatus> getGameStatus(String gameStatusName);
    void deleteByGameStatusName(String gameStatusName);
}
