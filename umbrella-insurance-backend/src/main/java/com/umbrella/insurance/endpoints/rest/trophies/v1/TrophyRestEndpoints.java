package com.umbrella.insurance.endpoints.rest.trophies.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.trophies.v1.db.TrophyPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.trophies.v1.db.jpa.TrophyService;
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
@RequestMapping(TrophyPrivilege.PATH)
public class TrophyRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(TrophyRestEndpoints.class);

    @Autowired
    private TrophyService trophyService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Trophy createTrophy(
            @RequestParam String env,
            @RequestBody Trophy trophy,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Trophy trophyResponse;
        try {
            request.setAttribute("requestBody", trophy);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            trophyResponse = trophyService.saveTrophy(trophy);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create trophy ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", trophyResponse);
        return trophyResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Trophy> readTrophies(
            @RequestParam String env,
            @RequestParam(required = false) Long trophyId,
            @RequestParam(required = false) String trophyName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Trophy> trophyList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (trophyId != null) {
                Optional<Trophy> trophy = trophyService.getTrophyById(
                        trophyId);
                if (trophy.isEmpty()) {
                    throw new NotFoundException("Unable to read trophy ");
                } else {
                    trophyList = new ArrayList<>();
                    trophyList.add(trophy.get());
                }
            } else if (trophyName != null) {
                Optional<Trophy> trophy = trophyService
                        .getTrophyByTrophyName(trophyName);
                if (trophy.isEmpty()) {
                    throw new NotFoundException("Unable to read trophy ");
                } else {
                    trophyList = new ArrayList<>();
                    trophyList.add(trophy.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented trophy ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read trophy ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", trophyList);
        return trophyList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Trophy> updateTrophies(
            @RequestParam String env,
            @RequestBody Trophy trophy,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Trophy> trophyList;
        try {
            request.setAttribute("requestBody", trophy);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            trophy = trophyService.updateTrophy(
                    trophy);
            trophyList = new ArrayList<>();
            trophyList.add(trophy);

        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update trophy ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", trophyList);
        return trophyList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTrophys(
            @RequestParam String env,
            @RequestParam(required = false) Long trophyId,
            @RequestParam(required = false) String trophyName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(trophyId != null) {
                trophyService.deleteTrophy(
                        trophyId);
            } else if (trophyName != null) {
                trophyService
                        .deleteTrophyByTrophyName(
                                trophyName);
            } else {
                throw new NotImplementedException("This delete query is not implemented trophy ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete trophy ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
