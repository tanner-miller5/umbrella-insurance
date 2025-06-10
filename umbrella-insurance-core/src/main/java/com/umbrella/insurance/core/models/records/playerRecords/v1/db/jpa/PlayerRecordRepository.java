package com.umbrella.insurance.core.models.records.playerRecords.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.PlayerRecord;

import java.util.Optional;

@Repository
public interface PlayerRecordRepository extends JpaRepository<PlayerRecord, Long> {
    Optional<PlayerRecord> findBySeasonIdAndPlayerId(Long seasonId, Long playerRecordId);
    void deletePlayerRecordBySeasonIdAndPlayerId(Long seasonId, Long playerRecordId);
}
