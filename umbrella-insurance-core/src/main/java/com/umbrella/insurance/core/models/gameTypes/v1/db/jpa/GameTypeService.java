package com.umbrella.insurance.core.models.gameTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.GameType;

import java.util.List;
import java.util.Optional;

public interface GameTypeService {
    GameType saveGameType(GameType gameType);
    List<GameType> getGameTypes();
    GameType updateGameType(GameType gameType);
    void deleteGameType(Long gameTypeId);
    Optional<GameType> getGameTypeByGameTypeName(String gameTypeName);
    Optional<GameType> getGameTypeByGameId(Long gameTypeId);
    void deleteByGameTypeName(String gameTypeName);
}
