package com.umbrella.insurance.endpoints.rest.users.policies.pendingPolicies.pendingPolicyStates.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.PendingPolicyState;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyStates.v1.db.PendingPolicyStatePrivilege;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyStates.v1.db.jpa.PendingPolicyStateService;
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
@RequestMapping(PendingPolicyStatePrivilege.PATH)
public class PendingPolicyStateRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(PendingPolicyStateRestEndpoints.class);

    @Autowired
    private PendingPolicyStateService pendingPolicyStateService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PendingPolicyState createPendingPolicyState(
            @RequestParam String env,
            @RequestBody PendingPolicyState pendingPolicyState,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        PendingPolicyState pendingPolicyStateResponse;
        try {
            request.setAttribute("requestBody", pendingPolicyState);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            pendingPolicyStateResponse = pendingPolicyStateService.savePendingPolicyState(pendingPolicyState);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create pendingPolicyState ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingPolicyStateResponse);
        return pendingPolicyStateResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PendingPolicyState> readPendingPolicyStates(
            @RequestParam String env,
            @RequestParam(required = false) Long pendingPolicyStateId,
            @RequestParam(required = false) String pendingPolicyStateName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PendingPolicyState> pendingPolicyStateList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (pendingPolicyStateId != null) {
                Optional<PendingPolicyState> pendingPolicyState = pendingPolicyStateService
                        .getPendingPolicyStateById(pendingPolicyStateId);
                if (pendingPolicyState.isEmpty()) {
                    throw new NotFoundException("Unable to read pendingPolicyState ");
                } else {
                    pendingPolicyStateList = new ArrayList<>();
                    pendingPolicyStateList.add(pendingPolicyState.get());
                }
            } else if (pendingPolicyStateName != null) {
                Optional<PendingPolicyState> pendingPolicyState = pendingPolicyStateService
                        .getPendingPolicyStateName(pendingPolicyStateName);
                if (pendingPolicyState.isEmpty()) {
                    throw new NotFoundException("Unable to read pendingPolicyState");
                } else {
                    pendingPolicyStateList = new ArrayList<>();
                    pendingPolicyStateList.add(pendingPolicyState.get());
                }
            } else {
                pendingPolicyStateList = pendingPolicyStateService.getPendingPolicyStates();
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read pendingPolicyState");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingPolicyStateList);
        return pendingPolicyStateList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PendingPolicyState> updatePendingPolicyStates(
            @RequestParam String env,
            @RequestBody PendingPolicyState pendingPolicyState,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PendingPolicyState> pendingPolicyStateList;
        try {
            request.setAttribute("requestBody", pendingPolicyState);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            pendingPolicyState = pendingPolicyStateService.updatePendingPolicyState(
                    pendingPolicyState);
            pendingPolicyStateList = new ArrayList<>();
            pendingPolicyStateList.add(pendingPolicyState);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update pendingPolicyState");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingPolicyStateList);
        return pendingPolicyStateList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePendingPolicyStates(
            @RequestParam String env,
            @RequestParam(required = false) Long pendingPolicyStateId,
            @RequestParam(required = false) String pendingPolicyStateName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(pendingPolicyStateId != null) {
                pendingPolicyStateService.deletePendingPolicyState(
                        pendingPolicyStateId);
            } else if (pendingPolicyStateName != null) {
                pendingPolicyStateService
                        .deletePendingPolicyStateByPendingPolicyStateName(pendingPolicyStateName);
            } else {
                throw new NotImplementedException("This delete query is not implemented pendingPolicyState");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete pendingPolicyState ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
