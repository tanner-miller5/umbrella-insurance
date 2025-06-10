package com.umbrella.insurance.core.models.games.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GameServiceImpl implements GameService {
    @Autowired
    GameRepository gameRepository;

    @Override
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public List<Game> getGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game updateGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void deleteGame(Long gameId) {
        gameRepository.deleteById(gameId);
    }

    @Override
    public Optional<Game> getGame(Long gameId) {
        return gameRepository.findById(gameId);
    }

    @Override
    public Optional<Game> getGame(String gameName) {
        return gameRepository.findGameByGameName(gameName);
    }

    @Override
    public void deleteGameByGameName(String gameName) {
        gameRepository.deleteByGameName(gameName);
    }
}
