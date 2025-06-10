package com.umbrella.insurance.endpoints.rest.seasons.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.seasons.v1.db.SeasonPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.seasons.v1.db.jpa.SeasonService;
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
@RequestMapping(SeasonPrivilege.PATH)
public class SeasonRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(SeasonRestEndpoints.class);

    @Autowired
    private SeasonService seasonService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Season createSeason(
            @RequestParam String env,
            @RequestBody Season season,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Season seasonResponse;
        try {
            request.setAttribute("requestBody", season);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            seasonResponse = seasonService.saveSeason(season);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create season ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", seasonResponse);
        return seasonResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Season> readSeasons(
            @RequestParam String env,
            @RequestParam(required = false) Long seasonId,
            @RequestParam(required = false) String seasonName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Season> seasonList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (seasonId != null) {
                Optional<Season> season = seasonService.getSeasonById(
                        seasonId);
                if (season.isEmpty()) {
                    throw new NotFoundException("Unable to read season ");
                } else {
                    seasonList = new ArrayList<>();
                    seasonList.add(season.get());
                }
            } else if (seasonName != null) {
                Optional<Season> season = seasonService
                        .getSeasonBySeasonName(seasonName);
                if (season.isEmpty()) {
                    throw new NotFoundException("Unable to read season ");
                } else {
                    seasonList = new ArrayList<>();
                    seasonList.add(season.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented season ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read season ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", seasonList);
        return seasonList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Season> updateSeasons(
            @RequestParam String env,
            @RequestBody Season season,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Season> seasonList;
        try {
            request.setAttribute("requestBody", season);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            season = seasonService.updateSeason(
                    season);
            seasonList = new ArrayList<>();
            seasonList.add(season);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update season ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", seasonList);
        return seasonList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSeasons(
            @RequestParam String env,
            @RequestParam(required = false) Long seasonId,
            @RequestParam(required = false) String seasonName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(seasonId != null) {
                seasonService.deleteSeason(
                        seasonId);
            } else if (seasonName != null) {
                seasonService
                        .deleteSeasonBySeasonName(
                                seasonName);
            } else {
                throw new NotImplementedException("This delete query is not implemented season ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete season ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
