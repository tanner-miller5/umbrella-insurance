package com.umbrella.insurance.endpoints.rest.users.policies.matchedPolicies.matchedPolicyStates.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.policies.matchedPolicies.matchedPolicyStates.v1.db.MatchedPolicyStatePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.policies.matchedPolicies.matchedPolicyStates.v1.db.jpa.MatchedPolicyStateService;
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
@RequestMapping(MatchedPolicyStatePrivilege.PATH)
public class MatchedPolicyStateRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(MatchedPolicyStateRestEndpoints.class);

    @Autowired
    private MatchedPolicyStateService matchedPolicyStateService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    MatchedPolicyState createMatchedPolicyState(
            @RequestParam String env,
            @RequestBody MatchedPolicyState matchedPolicyState,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        MatchedPolicyState matchedPolicyStateResponse;
        try {
            request.setAttribute("requestBody", matchedPolicyState);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            matchedPolicyStateResponse = matchedPolicyStateService.saveMatchedPolicyState(matchedPolicyState);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create matchedPolicyState ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", matchedPolicyStateResponse);
        return matchedPolicyStateResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<MatchedPolicyState> readMatchedPolicyStates(
            @RequestParam String env,
            @RequestParam(required = false) Long matchedPolicyStateId,
            @RequestParam(required = false) String matchedPolicyStateName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<MatchedPolicyState> matchedPolicyStateList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (matchedPolicyStateId != null) {
                Optional<MatchedPolicyState> matchedPolicyState = matchedPolicyStateService.getMatchedPolicyStateByMatchedPolicyStateId(
                        matchedPolicyStateId);
                if (matchedPolicyState.isEmpty()) {
                    throw new NotFoundException("Unable to read matchedPolicyState");
                } else {
                    matchedPolicyStateList = new ArrayList<>();
                    matchedPolicyStateList.add(matchedPolicyState.get());
                }
            } else if (matchedPolicyStateName != null) {
                Optional<MatchedPolicyState> matchedPolicyState = matchedPolicyStateService
                        .getMatchedPolicyStateByMatchedPolicyStateName(matchedPolicyStateName);
                if (matchedPolicyState.isEmpty()) {
                    throw new NotFoundException("Unable to read matchedPolicyState");
                } else {
                    matchedPolicyStateList = new ArrayList<>();
                    matchedPolicyStateList.add(matchedPolicyState.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented matchedPolicyState ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read matchedPolicyState ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", matchedPolicyStateList);
        return matchedPolicyStateList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<MatchedPolicyState> updateMatchedPolicyStates(
            @RequestParam String env,
            @RequestBody MatchedPolicyState matchedPolicyState,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<MatchedPolicyState> matchedPolicyStateList;
        try {
            request.setAttribute("requestBody", matchedPolicyState);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            matchedPolicyState = matchedPolicyStateService.updateMatchedPolicyState(
                    matchedPolicyState);
            matchedPolicyStateList = new ArrayList<>();
            matchedPolicyStateList.add(matchedPolicyState);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update matchedPolicyState ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", matchedPolicyStateList);
        return matchedPolicyStateList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMatchedPolicyStates(
            @RequestParam String env,
            @RequestParam(required = false) Long matchedPolicyStateId,
            @RequestParam(required = false) String matchedPolicyStateName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(matchedPolicyStateId != null) {
                matchedPolicyStateService.deleteMatchedPolicyState(matchedPolicyStateId);
            } else if (matchedPolicyStateName != null) {
                matchedPolicyStateService
                        .deleteMatchedPolicyStateByMatchedPolicyStateName(
                                matchedPolicyStateName);
            } else {
                throw new NotImplementedException("This delete query is not implemented matchedPolicyState ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete matchedPolicyState ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
