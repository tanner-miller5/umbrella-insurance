package com.umbrella.insurance.endpoints.rest.users.bets.pendingBets.pendingBetStates.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.bets.pendingBets.pendingBetStates.v1.db.PendingBetStatePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.bets.pendingBets.pendingBetStates.v1.db.jpa.PendingBetStateService;
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
@RequestMapping(PendingBetStatePrivilege.PATH)
public class PendingBetStateRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(PendingBetStateRestEndpoints.class);

    @Autowired
    private PendingBetStateService pendingBetStateService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PendingBetState createPendingBetState(
            @RequestParam String env,
            @RequestBody PendingBetState pendingBetState,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        PendingBetState pendingBetStateResponse;
        try {
            request.setAttribute("requestBody", pendingBetState);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            pendingBetStateResponse = pendingBetStateService.savePendingBetState(pendingBetState);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create pendingBetState ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingBetStateResponse);
        return pendingBetStateResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PendingBetState> readPendingBetStates(
            @RequestParam String env,
            @RequestParam(required = false) Long pendingBetStateId,
            @RequestParam(required = false) String pendingBetStateName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PendingBetState> pendingBetStateList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (pendingBetStateId != null) {
                Optional<PendingBetState> pendingBetState = pendingBetStateService.getPendingBetStateByPendingBetStateId(
                        pendingBetStateId);
                if (pendingBetState.isEmpty()) {
                    throw new NotFoundException("Unable to read pendingBetState ");
                } else {
                    pendingBetStateList = new ArrayList<>();
                    pendingBetStateList.add(pendingBetState.get());
                }
            } else if (pendingBetStateName != null) {
                Optional<PendingBetState> pendingBetState = pendingBetStateService
                        .getPendingBetStateByPendingBetStateName(pendingBetStateName);
                if (pendingBetState.isEmpty()) {
                    throw new NotFoundException("Unable to read pendingBetState ");
                } else {
                    pendingBetStateList = new ArrayList<>();
                    pendingBetStateList.add(pendingBetState.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented pendingBetState ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read pendingBetState ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingBetStateList);
        return pendingBetStateList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PendingBetState> updatePendingBetStates(
            @RequestParam String env,
            @RequestBody PendingBetState pendingBetState,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PendingBetState> pendingBetStateList;
        try {
            request.setAttribute("requestBody", pendingBetState);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            pendingBetState = pendingBetStateService.updatePendingBetState(
                    pendingBetState);
            pendingBetStateList = new ArrayList<>();
            pendingBetStateList.add(pendingBetState);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update pendingBetState ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingBetStateList);
        return pendingBetStateList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePendingBetStates(
            @RequestParam String env,
            @RequestParam(required = false) Long pendingBetStateId,
            @RequestParam(required = false) String pendingBetStateName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(pendingBetStateId != null) {
                pendingBetStateService.deletePendingBetState(
                        pendingBetStateId);
            } else if (pendingBetStateName != null) {
                pendingBetStateService
                        .deletePendingBetStateByPendingBetStateName(
                                pendingBetStateName);
            } else {
                throw new NotImplementedException("This delete query is not implemented pendingBetState ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete pendingBetState ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
