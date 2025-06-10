package com.umbrella.insurance.endpoints.rest.users.bets.matchedBets.matchedBetStates.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.bets.matchedBets.matchedBetStates.v1.db.MatchedBetStatePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.bets.matchedBets.matchedBetStates.v1.db.jpa.MatchedBetStateService;
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
@RequestMapping(MatchedBetStatePrivilege.PATH)
public class MatchedBetStateRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(MatchedBetStateRestEndpoints.class);

    @Autowired
    private MatchedBetStateService matchedBetStateService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    MatchedBetState createMatchedBetState(
            @RequestParam String env,
            @RequestBody MatchedBetState matchedBetState,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        MatchedBetState matchedBetStateResponse;
        try {
            request.setAttribute("requestBody", matchedBetState);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            matchedBetStateResponse = matchedBetStateService.saveMatchedBetState(matchedBetState);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create matchedBetState ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", matchedBetStateResponse);
        return matchedBetStateResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<MatchedBetState> readMatchedBetStates(
            @RequestParam String env,
            @RequestParam(required = false) Long matchedBetStateId,
            @RequestParam(required = false) String matchedBetStateName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<MatchedBetState> matchedBetStateList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (matchedBetStateId != null) {
                Optional<MatchedBetState> matchedBetState = matchedBetStateService.getMatchedBetStateByMatchedBetStateId(
                        matchedBetStateId);
                if (matchedBetState.isEmpty()) {
                    throw new NotFoundException("Unable to read matchedBetState ");
                } else {
                    matchedBetStateList = new ArrayList<>();
                    matchedBetStateList.add(matchedBetState.get());
                }
            } else if (matchedBetStateName != null) {
                Optional<MatchedBetState> matchedBetState = matchedBetStateService
                        .getMatchedBetStateByMatchedBetStateName(matchedBetStateName);
                if (matchedBetState.isEmpty()) {
                    throw new NotFoundException("Unable to read matchedBetState ");
                } else {
                    matchedBetStateList = new ArrayList<>();
                    matchedBetStateList.add(matchedBetState.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented matchedBetState ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read matchedBetState ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", matchedBetStateList);
        return matchedBetStateList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<MatchedBetState> updateMatchedBetStates(
            @RequestParam String env,
            @RequestBody MatchedBetState matchedBetState,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<MatchedBetState> matchedBetStateList;
        try {
            request.setAttribute("requestBody", matchedBetState);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            matchedBetState = matchedBetStateService.updateMatchedBetState(
                    matchedBetState);
            matchedBetStateList = new ArrayList<>();
            matchedBetStateList.add(matchedBetState);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update matchedBetState ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", matchedBetStateList);
        return matchedBetStateList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMatchedBetStates(
            @RequestParam String env,
            @RequestParam(required = false) Long matchedBetStateId,
            @RequestParam(required = false) String matchedBetStateName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(matchedBetStateId != null) {
                matchedBetStateService.deleteMatchedBetState(
                        matchedBetStateId);
            } else if (matchedBetStateName != null) {
                matchedBetStateService
                        .deleteMatchedBetStateByMatchedBetStateName(
                                matchedBetStateName);
            } else {
                throw new NotImplementedException("This delete query is not implemented matchedBetState");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete matchedBetState");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
