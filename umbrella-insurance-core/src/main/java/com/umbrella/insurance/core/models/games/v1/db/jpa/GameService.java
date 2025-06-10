package com.umbrella.insurance.core.models.games.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Game;

import java.util.List;
import java.util.Optional;

public interface GameService {
    Game saveGame(Game game);
    List<Game> getGames();
    Game updateGame(Game game);
    void deleteGame(Long gameId);
    Optional<Game> getGame(Long gameId);
    Optional<Game> getGame(String gameName);
    void deleteGameByGameName(String gameName);
}
