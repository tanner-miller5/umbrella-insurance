package com.umbrella.insurance.endpoints.rest.people.referees.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.people.referees.v1.db.RefereePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.people.referees.v1.db.jpa.RefereeService;
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
@RequestMapping(RefereePrivilege.PATH)
public class RefereeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(RefereeRestEndpoints.class);

    @Autowired
    private RefereeService refereeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Referee createReferee(
            @RequestParam String env,
            @RequestBody Referee referee,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Referee refereeResponse;
        try {
            request.setAttribute("requestBody", referee);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            refereeResponse = refereeService.saveReferee(referee);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create referee ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", refereeResponse);
        return refereeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Referee> readReferees(
            @RequestParam String env,
            @RequestParam(required = false) Long refereeId,
            @RequestParam(required = false) Long personId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Referee> refereeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (refereeId != null) {
                Optional<Referee> referee = refereeService
                        .getReferee(
                        refereeId);
                if (referee.isEmpty()) {
                    throw new NotFoundException("Unable to read referee ");
                } else {
                    refereeList = new ArrayList<>();
                    refereeList.add(referee.get());
                }
            } else if (personId != null) {
                Optional<Referee> referee = refereeService
                        .getRefereeByPersonId(personId);
                if (referee.isEmpty()) {
                    throw new NotFoundException("Unable to read referee ");
                } else {
                    refereeList = new ArrayList<>();
                    refereeList.add(referee.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented referee ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read referee ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", refereeList);
        return refereeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Referee> updateReferees(
            @RequestParam String env,
            @RequestBody Referee referee,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Referee> refereeList;
        try {
            request.setAttribute("requestBody", referee);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            referee = refereeService.updateReferee(
                    referee);
            refereeList = new ArrayList<>();
            refereeList.add(referee);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update referee ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", refereeList);
        return refereeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteReferees(
            @RequestParam String env,
            @RequestParam(required = false) Long refereeId,
            @RequestParam(required = false) Long personId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(refereeId != null) {
                refereeService.deleteReferee(refereeId);
            } else if (personId != null) {
                refereeService
                        .deleteRefereeByPersonId(
                                personId);

            } else {
                throw new NotImplementedException("This delete query is not implemented referee ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete referee ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
