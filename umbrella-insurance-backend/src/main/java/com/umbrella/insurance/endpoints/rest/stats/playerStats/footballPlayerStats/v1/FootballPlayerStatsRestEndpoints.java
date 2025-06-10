package com.umbrella.insurance.endpoints.rest.stats.playerStats.footballPlayerStats.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.stats.playerStats.footballPlayerStats.v1.db.FootballPlayerStatsPrivilege;
import com.umbrella.insurance.core.models.stats.playerStats.footballPlayerStats.v1.db.jpa.FootballPlayerStatsService;
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

import com.umbrella.insurance.core.models.entities.*;

@Controller
@RequestMapping(FootballPlayerStatsPrivilege.PATH)
public class FootballPlayerStatsRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(FootballPlayerStatsRestEndpoints.class);

    @Autowired
    private FootballPlayerStatsService footballPlayerStatsService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    FootballPlayerStats createFootballPlayerStats(
            @RequestParam String env,
            @RequestBody FootballPlayerStats footballPlayerStats,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        FootballPlayerStats footballPlayerStatsResponse;
        try {
            request.setAttribute("requestBody", footballPlayerStats);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            footballPlayerStatsResponse = footballPlayerStatsService.saveFootballPlayerStats(footballPlayerStats);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create footballPlayerStats ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", footballPlayerStatsResponse);
        return footballPlayerStatsResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<FootballPlayerStats> readFootballPlayerStats(
            @RequestParam String env,
            @RequestParam(required = false) Long footballPlayerStatsId,
            @RequestParam(required = false) Long gameId,
            @RequestParam(required = false) Long playerId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<FootballPlayerStats> footballPlayerStatsList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (footballPlayerStatsId != null) {
                Optional<FootballPlayerStats> footballPlayerStats = footballPlayerStatsService
                        .getFootballPlayerStatsById(
                        footballPlayerStatsId);
                if (footballPlayerStats.isEmpty()) {
                    throw new NotFoundException("Unable to read footballPlayerStats ");
                } else {
                    footballPlayerStatsList = new ArrayList<>();
                    footballPlayerStatsList.add(footballPlayerStats.get());
                }
            } else if (gameId != null && playerId != null) {
                Optional<FootballPlayerStats> footballPlayerStats = footballPlayerStatsService
                        .getFootballPlayerStatsByGameIdAndPlayerId(
                                gameId, playerId);
                if (footballPlayerStats.isEmpty()) {
                    throw new NotFoundException("Unable to read footballPlayerStats ");
                } else {
                    footballPlayerStatsList = new ArrayList<>();
                    footballPlayerStatsList.add(footballPlayerStats.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented footballPlayerStats ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read footballPlayerStats ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", footballPlayerStatsList);
        return footballPlayerStatsList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<FootballPlayerStats> updateFootballPlayerStats(
            @RequestParam String env,
            @RequestBody FootballPlayerStats footballPlayerStats,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<FootballPlayerStats> footballPlayerStatsList;
        try {
            request.setAttribute("requestBody", footballPlayerStats);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            footballPlayerStatsService.updateFootballPlayerStats(
                        footballPlayerStats);
            footballPlayerStatsList = new ArrayList<>();
            footballPlayerStatsList.add(footballPlayerStats);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update footballPlayerStats ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", footballPlayerStatsList);
        return footballPlayerStatsList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteFootballPlayerStats(
            @RequestParam String env,
            @RequestParam(required = false) Long footballPlayerStatsId,
            @RequestParam(required = false) Long gameId,
            @RequestParam(required = false) Long playerId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(footballPlayerStatsId != null) {
                footballPlayerStatsService.deleteFootballPlayerStats(
                        footballPlayerStatsId);
            } else if (gameId != null && playerId != null) {
                footballPlayerStatsService
                        .deleteFootballPlayerStatsByGameIdAndPlayerId(
                                gameId, playerId);
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete footballPlayerStats ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
