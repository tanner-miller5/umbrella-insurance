package com.umbrella.insurance.core.models.records.playerRecords.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umbrella.insurance.core.models.entities.PlayerRecord;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlayerRecordServiceImpl implements PlayerRecordService {
    @Autowired
    PlayerRecordRepository playerRecordRepository;

    @Override
    public PlayerRecord savePlayerRecord(PlayerRecord playerRecord) {
        return playerRecordRepository.save(playerRecord);
    }

    @Override
    public List<PlayerRecord> getPlayerRecords() {
        return playerRecordRepository.findAll();
    }

    @Override
    public PlayerRecord updatePlayerRecord(PlayerRecord playerRecord) {
        return playerRecordRepository.save(playerRecord);
    }

    @Override
    public void deletePlayerRecord(Long playerRecordId) {
        playerRecordRepository.deleteById(playerRecordId);
    }

    @Override
    public Optional<PlayerRecord> getPlayerRecord(Long playerRecordId) {
        return playerRecordRepository.findById(playerRecordId);
    }

    @Override
    public Optional<PlayerRecord> getPlayerRecordBySeasonIdAndPlayerId(Long seasonId, Long playerRecordId) {
        return playerRecordRepository.findBySeasonIdAndPlayerId(seasonId, playerRecordId);
    }

    @Override
    public void deletePlayerRecordBySeasonIdAndPlayerId(Long seasonId, Long playerRecordId) {
        playerRecordRepository.deletePlayerRecordBySeasonIdAndPlayerId(seasonId, playerRecordId);
    }
}
