package com.umbrella.insurance.endpoints.rest.leagues.conferences.divisions.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.leagues.conferences.divisions.v1.db.DivisionPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.leagues.conferences.divisions.v1.db.jpa.DivisionService;
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
@RequestMapping(DivisionPrivilege.PATH)
public class DivisionRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(DivisionRestEndpoints.class);

    @Autowired
    private DivisionService divisionService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Division createDivision(
            @RequestParam String env,
            @RequestBody Division division,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Division divisionResponse;
        try {
            request.setAttribute("requestBody", division);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            divisionResponse = divisionService.saveDivision(division);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create division ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", divisionResponse);
        return divisionResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Division> readDivisions(
            @RequestParam String env,
            @RequestParam(required = false) Long divisionId,
            @RequestParam(required = false) String divisionName,
            @RequestParam(required = false) Long conferenceId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Division> divisionList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (divisionId != null) {
                Optional<Division> division = divisionService.getDivisionById(divisionId);
                if (division.isEmpty()) {
                    throw new NotFoundException("Unable to read division ");
                } else {
                    divisionList = new ArrayList<>();
                    divisionList.add(division.get());
                }
            } else if (divisionName != null) {
                Optional<Division> division = divisionService
                        .getDivisionByConferenceIdAndDivisionName(
                                conferenceId, divisionName);
                if (division.isEmpty()) {
                    throw new NotFoundException("Unable to read division ");
                } else {
                    divisionList = new ArrayList<>();
                    divisionList.add(division.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented division ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read division ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", divisionList);
        return divisionList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Division> updateDivisions(
            @RequestParam String env,
            @RequestBody Division division,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Division> divisionList;
        try {
            request.setAttribute("requestBody", division);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            division = divisionService.updateDivision(
                    division);
            divisionList = new ArrayList<>();
            divisionList.add(division);

        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update division ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", divisionList);
        return divisionList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteDivisions(
            @RequestParam String env,
            @RequestParam(required = false) Long divisionId,
            @RequestParam(required = false) String divisionName,
            @RequestParam(required = false) Long conferenceId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(divisionId != null) {
                divisionService.deleteDivision(divisionId);
            } else if (divisionName != null) {
                divisionService.deleteDivisionByConferenceIdAndDivisionName(
                                conferenceId, divisionName);
            } else {
                throw new NotImplementedException("This delete query is not implemented division ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete division ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
