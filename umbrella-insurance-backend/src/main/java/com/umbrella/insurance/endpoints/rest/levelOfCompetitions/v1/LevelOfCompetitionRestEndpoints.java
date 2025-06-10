package com.umbrella.insurance.endpoints.rest.levelOfCompetitions.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.levelOfCompetitions.v1.db.LevelOfCompetitionPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.levelOfCompetitions.v1.db.jpa.LevelOfCompetitionService;
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
@RequestMapping(LevelOfCompetitionPrivilege.PATH)
public class LevelOfCompetitionRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(LevelOfCompetitionRestEndpoints.class);

    @Autowired
    private LevelOfCompetitionService levelOfCompetitionService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    LevelOfCompetition createLevelOfCompetition(
            @RequestParam String env,
            @RequestBody LevelOfCompetition levelOfCompetition,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        LevelOfCompetition levelOfCompetitionResponse;
        try {
            request.setAttribute("requestBody", levelOfCompetition);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            levelOfCompetitionResponse = levelOfCompetitionService.saveLevelOfCompetition(levelOfCompetition);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create levelOfCompetition ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", levelOfCompetitionResponse);
        return levelOfCompetitionResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<LevelOfCompetition> readLevelOfCompetitions(
            @RequestParam String env,
            @RequestParam(required = false) Long levelOfCompetitionId,
            @RequestParam(required = false) String levelOfCompetitionName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<LevelOfCompetition> levelOfCompetitionList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (levelOfCompetitionId != null) {
                Optional<LevelOfCompetition> levelOfCompetition = levelOfCompetitionService.getLevelOfCompetitionById(
                        levelOfCompetitionId);
                if (levelOfCompetition.isEmpty()) {
                    throw new NotFoundException("Unable to read levelOfCompetition ");
                } else {
                    levelOfCompetitionList = new ArrayList<>();
                    levelOfCompetitionList.add(levelOfCompetition.get());
                }
            } else if (levelOfCompetitionName != null) {
                Optional<LevelOfCompetition> levelOfCompetition = levelOfCompetitionService
                        .getLevelOfCompetitionByLevelOfCompetitionName(levelOfCompetitionName);
                if (levelOfCompetition.isEmpty()) {
                    throw new NotFoundException("Unable to read levelOfCompetition ");
                } else {
                    levelOfCompetitionList = new ArrayList<>();
                    levelOfCompetitionList.add(levelOfCompetition.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented levelOfCompetition ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read levelOfCompetition ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", levelOfCompetitionList);
        return levelOfCompetitionList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<LevelOfCompetition> updateLevelOfCompetitions(
            @RequestParam String env,
            @RequestBody LevelOfCompetition levelOfCompetition,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<LevelOfCompetition> levelOfCompetitionList;
        try {
            request.setAttribute("requestBody", levelOfCompetition);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            levelOfCompetition = levelOfCompetitionService.updateLevelOfCompetition(
                    levelOfCompetition);
            levelOfCompetitionList = new ArrayList<>();
            levelOfCompetitionList.add(levelOfCompetition);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update levelOfCompetition ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", levelOfCompetitionList);
        return levelOfCompetitionList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteLevelOfCompetitions(
            @RequestParam String env,
            @RequestParam(required = false) Long levelOfCompetitionId,
            @RequestParam(required = false) String levelOfCompetitionName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(levelOfCompetitionId != null) {
                levelOfCompetitionService.deleteLevelOfCompetition(
                        levelOfCompetitionId);
            } else if (levelOfCompetitionName != null) {
                levelOfCompetitionService
                        .deleteLevelOfCompetitionByLevelOfCompetitionName(
                                levelOfCompetitionName);
            } else {
                throw new NotImplementedException("This delete query is not implemented levelOfCompetition ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete levelOfCompetition ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
