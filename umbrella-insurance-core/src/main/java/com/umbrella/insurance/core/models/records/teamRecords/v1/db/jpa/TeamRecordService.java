package com.umbrella.insurance.core.models.records.teamRecords.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.TeamRecord;

import java.util.List;
import java.util.Optional;

public interface TeamRecordService {
    TeamRecord saveTeamRecord(TeamRecord teamRecord);
    List<TeamRecord> getTeamRecords();
    TeamRecord updateTeamRecord(TeamRecord teamRecord);
    void deleteTeamRecord(Long teamRecordId);
    Optional<TeamRecord> getTeamRecordById(Long teamRecordId);
    Optional<TeamRecord> getTeamRecordBySeasonIdAndTeamId(Long seasonId, Long teamId);
    void deleteTeamRecordBySeasonIdAndTeamId(Long seasonId, Long teamId);
}
