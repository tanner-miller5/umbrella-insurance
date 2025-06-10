package com.umbrella.insurance.core.models.games.gameStatuses.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.GameStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GameStatusServiceImpl implements GameStatusService {
    @Autowired
    GameStatusRepository gameStatusRepository;

    @Override
    public GameStatus saveGameStatus(GameStatus gameStatus) {
        return gameStatusRepository.save(gameStatus);
    }

    @Override
    public List<GameStatus> getGameStatuses() {
        return gameStatusRepository.findAll();
    }

    @Override
    public GameStatus updateGameStatus(GameStatus gameStatus) {
        return gameStatusRepository.save(gameStatus);
    }

    @Override
    public void deleteGameStatus(Long gameStatusId) {
        gameStatusRepository.deleteById(gameStatusId);
    }

    @Override
    public Optional<GameStatus> getGameStatus(Long gameStatusId) {
        return gameStatusRepository.findById(gameStatusId);
    }

    @Override
    public Optional<GameStatus> getGameStatus(String gameStatusName) {
        return gameStatusRepository.findByGameStatusName(gameStatusName);
    }

    @Override
    public void deleteByGameStatusName(String gameStatusName) {
        gameStatusRepository.deleteByGameStatusName(gameStatusName);
    }
}
