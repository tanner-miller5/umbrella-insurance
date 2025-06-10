package com.umbrella.insurance.core.models.gameTypes.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.GameType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GameTypeServiceImpl implements GameTypeService {
    @Autowired
    GameTypeRepository gameTypeRepository;

    @Override
    public GameType saveGameType(GameType gameType) {
        return gameTypeRepository.save(gameType);
    }

    @Override
    public List<GameType> getGameTypes() {
        return gameTypeRepository.findAll();
    }

    @Override
    public GameType updateGameType(GameType gameType) {
        return gameTypeRepository.save(gameType);
    }

    @Override
    public void deleteGameType(Long gameTypeId) {
        gameTypeRepository.deleteById(gameTypeId);
    }

    @Override
    public Optional<GameType> getGameTypeByGameTypeName(String gameTypeName) {
        return gameTypeRepository.findByGameTypeName(gameTypeName);
    }

    @Override
    public Optional<GameType> getGameTypeByGameId(Long gameTypeId) {
        return gameTypeRepository.findById(gameTypeId);
    }

    @Override
    public void deleteByGameTypeName(String gameTypeName) {
        gameTypeRepository.deleteByGameTypeName(gameTypeName);
    }
}
