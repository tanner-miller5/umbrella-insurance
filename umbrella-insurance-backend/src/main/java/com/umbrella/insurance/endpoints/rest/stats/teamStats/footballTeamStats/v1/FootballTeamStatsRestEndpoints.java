package com.umbrella.insurance.endpoints.rest.stats.teamStats.footballTeamStats.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.FootballTeamStats;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.stats.teamStats.footballTeamStats.v1.db.FootballTeamStatsPrivilege;
import com.umbrella.insurance.core.models.stats.teamStats.footballTeamStats.v1.db.jpa.FootballTeamStatsService;
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
@RequestMapping(FootballTeamStatsPrivilege.PATH)
public class FootballTeamStatsRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(FootballTeamStatsRestEndpoints.class);

    @Autowired
    private FootballTeamStatsService footballTeamStatsService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    FootballTeamStats createFootballTeamStats(
            @RequestParam String env,
            @RequestBody FootballTeamStats footballTeamStats,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        FootballTeamStats footballTeamStatsResponse;
        try {
            request.setAttribute("requestBody", footballTeamStats);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            footballTeamStatsResponse = footballTeamStatsService.saveFootballTeamStats(footballTeamStats);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create footballTeamStats ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", footballTeamStatsResponse);
        return footballTeamStatsResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<FootballTeamStats> readFootballTeamStats(
            @RequestParam String env,
            @RequestParam(required = false) Long footballTeamStatsId,
            @RequestParam(required = false) Long gameId,
            @RequestParam(required = false) Long teamId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<FootballTeamStats> footballTeamStatsList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (footballTeamStatsId != null) {
                Optional<FootballTeamStats> footballTeamStats = footballTeamStatsService.getFootballTeamStatsByFootballTeamStatsId(
                        footballTeamStatsId);
                if (footballTeamStats.isEmpty()) {
                    throw new NotFoundException("Unable to read footballTeamStats");
                } else {
                    footballTeamStatsList = new ArrayList<>();
                    footballTeamStatsList.add(footballTeamStats.get());
                }
            } else if (gameId != null && teamId != null) {
                Optional<FootballTeamStats> footballTeamStats = footballTeamStatsService
                        .getFootballTeamStatsByGameIdAndTeamId(
                                gameId, teamId);
                if (footballTeamStats.isEmpty()) {
                    throw new NotFoundException("Unable to read footballTeamStats");
                } else {
                    footballTeamStatsList = new ArrayList<>();
                    footballTeamStatsList.add(footballTeamStats.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented footballTeamStats");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read footballTeamStats");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", footballTeamStatsList);
        return footballTeamStatsList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<FootballTeamStats> updateFootballTeamStats(
            @RequestParam String env,
            @RequestBody FootballTeamStats footballTeamStats,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<FootballTeamStats> footballTeamStatsList;
        try {
            request.setAttribute("requestBody", footballTeamStats);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            footballTeamStats = footballTeamStatsService.updateFootballTeamStats(
                    footballTeamStats);
            footballTeamStatsList = new ArrayList<>();
            footballTeamStatsList.add(footballTeamStats);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update footballTeamStats");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", footballTeamStatsList);
        return footballTeamStatsList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteFootballTeamStats(
            @RequestParam String env,
            @RequestParam(required = false) Long footballTeamStatsId,
            @RequestParam(required = false) Long gameId,
            @RequestParam(required = false) Long teamId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(footballTeamStatsId != null) {
                footballTeamStatsService.deleteFootballTeamStats(
                        footballTeamStatsId);
            } else if (gameId != null && teamId != null) {
                footballTeamStatsService
                        .deleteFootballTeamStatsByGameIdAndTeamId(
                                gameId, teamId);
            } else {
                throw new NotImplementedException("This delete query is not implemented footballTeamStats ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete footballTeamStats ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
