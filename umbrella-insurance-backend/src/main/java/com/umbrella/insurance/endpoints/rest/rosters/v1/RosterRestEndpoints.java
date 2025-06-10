package com.umbrella.insurance.endpoints.rest.rosters.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.rosters.v1.db.RosterPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.rosters.v1.db.jpa.RosterService;
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
@RequestMapping(RosterPrivilege.PATH)
public class RosterRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(RosterRestEndpoints.class);

    @Autowired
    private RosterService rosterService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Roster createRoster(
            @RequestParam String env,
            @RequestBody Roster roster,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Roster rosterResponse;
        try {
            request.setAttribute("requestBody", roster);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            rosterResponse = rosterService.saveRoster(roster);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create roster ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", rosterResponse);
        return rosterResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Roster> readRosters(
            @RequestParam String env,
            @RequestParam(required = false) Long rosterId,
            @RequestParam(required = false) String rosterName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Roster> rosterList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (rosterId != null) {
                Optional<Roster> roster = rosterService.getRosterById(
                        rosterId);
                if (roster.isEmpty()) {
                    throw new NotFoundException("Unable to read roster ");
                } else {
                    rosterList = new ArrayList<>();
                    rosterList.add(roster.get());
                }
            } else if (rosterName != null) {
                Optional<Roster> roster = rosterService
                        .getRosterByRosterName(rosterName);
                if (roster.isEmpty()) {
                    throw new NotFoundException("Unable to read roster ");
                } else {
                    rosterList = new ArrayList<>();
                    rosterList.add(roster.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented roster ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read roster ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", rosterList);
        return rosterList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Roster> updateRosters(
            @RequestParam String env,
            @RequestBody Roster roster,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Roster> rosterList;
        try {
            request.setAttribute("requestBody", roster);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            roster = rosterService.updateRoster(roster);
            rosterList = new ArrayList<>();
            rosterList.add(roster);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update roster ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", rosterList);
        return rosterList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRosters(
            @RequestParam String env,
            @RequestParam(required = false) Long rosterId,
            @RequestParam(required = false) String rosterName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(rosterId != null) {
                rosterService.deleteRoster(
                        rosterId);
            } else if (rosterName != null) {
                rosterService
                        .deleteRosterByRosterName(
                                rosterName);
            } else {
                throw new NotImplementedException("This delete query is not implemented roster ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete roster ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
