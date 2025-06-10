package com.umbrella.insurance.core.models.records.teamRecords.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umbrella.insurance.core.models.entities.TeamRecord;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamRecordServiceImpl implements TeamRecordService {
    @Autowired
    TeamRecordRepository teamRecordRepository;

    @Override
    public TeamRecord saveTeamRecord(TeamRecord teamRecord) {
        return teamRecordRepository.save(teamRecord);
    }

    @Override
    public List<TeamRecord> getTeamRecords() {
        return teamRecordRepository.findAll();
    }

    @Override
    public TeamRecord updateTeamRecord(TeamRecord teamRecord) {
        return teamRecordRepository.save(teamRecord);
    }

    @Override
    public void deleteTeamRecord(Long teamRecordId) {
        teamRecordRepository.deleteById(teamRecordId);
    }

    @Override
    public Optional<TeamRecord> getTeamRecordById(Long teamRecordId) {
        return teamRecordRepository.findById(teamRecordId);
    }

    @Override
    public Optional<TeamRecord> getTeamRecordBySeasonIdAndTeamId(Long seasonId, Long teamId) {
        return teamRecordRepository.findTeamRecordBySeasonIdAndTeamId(seasonId, teamId);
    }

    @Override
    public void deleteTeamRecordBySeasonIdAndTeamId(Long seasonId, Long teamId) {
        teamRecordRepository.deleteTeamRecordBySeasonIdAndTeamId(seasonId, teamId);
    }
}
