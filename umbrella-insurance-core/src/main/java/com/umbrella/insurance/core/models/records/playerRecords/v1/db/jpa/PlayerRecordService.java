package com.umbrella.insurance.core.models.records.playerRecords.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.PlayerRecord;
import java.util.List;
import java.util.Optional;

public interface PlayerRecordService {
    PlayerRecord savePlayerRecord(PlayerRecord playerRecord);
    List<PlayerRecord> getPlayerRecords();
    PlayerRecord updatePlayerRecord(PlayerRecord playerRecord);
    void deletePlayerRecord(Long playerRecordId);
    Optional<PlayerRecord> getPlayerRecord(Long playerRecordId);
    Optional<PlayerRecord> getPlayerRecordBySeasonIdAndPlayerId(
            Long seasonId, Long playerRecordId);
    void deletePlayerRecordBySeasonIdAndPlayerId(Long seasonId, Long playerRecordId);
}
