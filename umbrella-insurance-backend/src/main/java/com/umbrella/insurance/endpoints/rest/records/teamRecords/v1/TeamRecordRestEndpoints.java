package com.umbrella.insurance.endpoints.rest.records.teamRecords.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.TeamRecord;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.records.teamRecords.v1.db.TeamRecordPrivilege;
import com.umbrella.insurance.core.models.records.teamRecords.v1.db.jpa.TeamRecordService;
import jakarta.servlet.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(TeamRecordPrivilege.PATH)
public class TeamRecordRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(TeamRecordRestEndpoints.class);

    @Autowired
    private TeamRecordService teamRecordService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    TeamRecord createTeamRecord(
            @RequestParam String env,
            @RequestBody TeamRecord teamRecord,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        TeamRecord teamRecordResponse;
        try {
            request.setAttribute("requestBody", teamRecord);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            teamRecordResponse = teamRecordService.saveTeamRecord(teamRecord);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create teamRecord record");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamRecordResponse);
        return teamRecordResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TeamRecord> readTeamRecords(
            @RequestParam String env,
            @RequestParam(required = false) Long teamRecordId,
            @RequestParam(required = false) Long seasonId,
            @RequestParam(required = false) Long teamId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<TeamRecord> teamRecordList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (teamRecordId != null) {
                Optional<TeamRecord> teamRecord = teamRecordService.getTeamRecordById(
                        teamRecordId);
                if (teamRecord.isEmpty()) {
                    throw new NotFoundException("Unable to read teamRecord record");
                } else {
                    teamRecordList = new ArrayList<>();
                    teamRecordList.add(teamRecord.get());
                }
            } else if (seasonId != null && teamId != null) {
                Optional<TeamRecord> teamRecord = teamRecordService
                        .getTeamRecordBySeasonIdAndTeamId(
                                seasonId, teamId);
                if (teamRecord.isEmpty()) {
                    throw new NotFoundException("Unable to read teamRecord record");
                } else {
                    teamRecordList = new ArrayList<>();
                    teamRecordList.add(teamRecord.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented teamRecord record");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read teamRecord record");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamRecordList);
        return teamRecordList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TeamRecord> updateTeamRecords(
            @RequestParam String env,
            @RequestBody TeamRecord teamRecord,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<TeamRecord> teamRecordList;
        try {
            request.setAttribute("requestBody", teamRecord);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            teamRecord = teamRecordService.updateTeamRecord(
                    teamRecord);
            teamRecordList = new ArrayList<>();
            teamRecordList.add(teamRecord);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update teamRecord record");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamRecordList);
        return teamRecordList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTeamRecordRecords(
            @RequestParam String env,
            @RequestParam(required = false) Long teamRecordId,
            @RequestParam(required = false) Long seasonId,
            @RequestParam(required = false) Long teamId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(teamRecordId != null) {
                teamRecordService.deleteTeamRecord(
                        teamRecordId);
            } else if (seasonId != null && teamId != null) {
                teamRecordService
                        .deleteTeamRecordBySeasonIdAndTeamId(
                                seasonId, teamId);
            } else {
                throw new NotImplementedException("This delete query is not implemented teamRecord record");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete teamRecord record");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
