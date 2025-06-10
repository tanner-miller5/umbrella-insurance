package com.umbrella.insurance.endpoints.rest.people.coaches.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.people.coaches.v1.db.CoachPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.people.coaches.v1.db.jpa.CoachService;
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
@RequestMapping(CoachPrivilege.PATH)
public class CoachRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(CoachRestEndpoints.class);

    @Autowired
    private CoachService coachService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Coach createCoach(
            @RequestParam String env,
            @RequestBody Coach coach,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Coach coachResponse;
        try {
            request.setAttribute("requestBody", coach);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            coachResponse = coachService.saveCoach(coach);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create coach ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", coachResponse);
        return coachResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Coach> readCoaches(
            @RequestParam String env,
            @RequestParam(required = false) Long coachId,
            @RequestParam(required = false) Long personId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Coach> coachList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (coachId != null) {
                Optional<Coach> coach = coachService.getCoachById(
                        coachId);
                if (coach.isEmpty()) {
                    throw new NotFoundException("Unable to read coach ");
                } else {
                    coachList = new ArrayList<>();
                    coachList.add(coach.get());
                }
            } else if (personId != null) {
                Optional<Coach> coach = coachService
                        .getCoachByPersonId(personId);
                if (coach.isEmpty()) {
                    throw new NotFoundException("Unable to read coach ");
                } else {
                    coachList = new ArrayList<>();
                    coachList.add(coach.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented coach ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read coach ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", coachList);
        return coachList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Coach> updateCoaches(
            @RequestParam String env,
            @RequestBody Coach coach,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Coach> coachList;
        try {
            request.setAttribute("requestBody", coach);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            coach = coachService.updateCoach(coach);
            coachList = new ArrayList<>();
            coachList.add(coach);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update coach ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", coachList);
        return coachList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCoaches(
            @RequestParam String env,
            @RequestParam(required = false) Long coachId,
            @RequestParam(required = false) Long personId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(coachId != null) {
                coachService.deleteCoach(
                        coachId);
            } else if (personId != null) {
                coachService.deleteCoachByPersonId(
                                personId);
            } else {
                throw new NotImplementedException("This delete query is not implemented coach ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete coach ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
