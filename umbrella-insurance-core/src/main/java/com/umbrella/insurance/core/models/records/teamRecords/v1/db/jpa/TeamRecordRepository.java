package com.umbrella.insurance.core.models.records.teamRecords.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRecordRepository extends JpaRepository<TeamRecord, Long> {
    Optional<TeamRecord> findTeamRecordBySeasonIdAndTeamId(Long seasonId, Long teamId);
    void deleteTeamRecordBySeasonIdAndTeamId(Long seasonId, Long teamId);
}
