package com.umbrella.insurance.endpoints.rest.seasons.seasonTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.SeasonTypePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.jpa.SeasonTypeService;
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
@RequestMapping(SeasonTypePrivilege.PATH)
public class SeasonTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(SeasonTypeRestEndpoints.class);

    @Autowired
    private SeasonTypeService seasonTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    SeasonType createSeasonType(
            @RequestParam String env,
            @RequestBody SeasonType seasonType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        SeasonType seasonTypeResponse;
        try {
            request.setAttribute("requestBody", seasonType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            seasonTypeResponse = seasonTypeService.saveSeasonType(seasonType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create seasonType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", seasonTypeResponse);
        return seasonTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<SeasonType> readSeasonTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long seasonTypeId,
            @RequestParam(required = false) String seasonTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<SeasonType> seasonTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (seasonTypeId != null) {
                Optional<SeasonType> seasonType = seasonTypeService.getSeasonTypeById(
                        seasonTypeId);
                if (seasonType.isEmpty()) {
                    throw new NotFoundException("Unable to read seasonType ");
                } else {
                    seasonTypeList = new ArrayList<>();
                    seasonTypeList.add(seasonType.get());
                }
            } else if (seasonTypeName != null) {
                Optional<SeasonType> seasonType = seasonTypeService
                        .getSeasonTypeBySeasonTypeName(seasonTypeName);
                if (seasonType.isEmpty()) {
                    throw new NotFoundException("Unable to read seasonType ");
                } else {
                    seasonTypeList = new ArrayList<>();
                    seasonTypeList.add(seasonType.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented seasonType ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read seasonType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", seasonTypeList);
        return seasonTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<SeasonType> updateSeasonTypes(
            @RequestParam String env,
            @RequestBody SeasonType seasonType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<SeasonType> seasonTypeList;
        try {
            request.setAttribute("requestBody", seasonType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            seasonType = seasonTypeService.updateSeasonType(seasonType);
            seasonTypeList = new ArrayList<>();
            seasonTypeList.add(seasonType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update seasonType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", seasonTypeList);
        return seasonTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSeasonTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long seasonTypeId,
            @RequestParam(required = false) String seasonTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(seasonTypeId != null) {
                seasonTypeService.deleteSeasonType(
                        seasonTypeId);
            } else if (seasonTypeName != null) {
                seasonTypeService
                        .deleteSeasonTypeBySeasonTypeName(
                                seasonTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented seasonType ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete seasonType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
