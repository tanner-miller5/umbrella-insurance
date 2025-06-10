package com.umbrella.insurance.endpoints.rest.users.policies.pendingPolicies.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.v1.db.PendingPolicyPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.v1.db.jpa.PendingPolicyService;
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
@RequestMapping(PendingPolicyPrivilege.PATH)
public class PendingPolicyRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(PendingPolicyRestEndpoints.class);

    @Autowired
    private PendingPolicyService pendingPolicyService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PendingPolicy createPendingPolicy(
            @RequestParam String env,
            @RequestBody PendingPolicy pendingPolicy,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        PendingPolicy pendingPolicyResponse;
        try {
            request.setAttribute("requestBody", pendingPolicy);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            pendingPolicyResponse = pendingPolicyService.savePendingPolicy(pendingPolicy);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create pendingPolicy");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingPolicyResponse);
        return pendingPolicyResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PendingPolicy> readPendingPolicies(
            @RequestParam String env,
            @RequestParam(required = false) Long pendingPolicyId,
            @RequestParam(required = false) String pendingPolicyName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PendingPolicy> pendingPolicyList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (pendingPolicyId != null) {
                Optional<PendingPolicy> pendingPolicy = pendingPolicyService.getPendingPolicyById(
                        pendingPolicyId);
                if (pendingPolicy.isEmpty()) {
                    throw new NotFoundException("Unable to read pendingPolicy");
                } else {
                    pendingPolicyList = new ArrayList<>();
                    pendingPolicyList.add(pendingPolicy.get());
                }
            } else if (pendingPolicyName != null) {
                Optional<PendingPolicy> pendingPolicy = pendingPolicyService
                        .getPendingPolicyByPendingPolicyName(pendingPolicyName);
                if (pendingPolicy.isEmpty()) {
                    throw new NotFoundException("Unable to read pendingPolicy");
                } else {
                    pendingPolicyList = new ArrayList<>();
                    pendingPolicyList.add(pendingPolicy.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented pendingPolicy ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read pendingPolicy");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingPolicyList);
        return pendingPolicyList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PendingPolicy> updatePendingPolicies(
            @RequestParam String env,
            @RequestBody PendingPolicy pendingPolicy,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PendingPolicy> pendingPolicyList;
        try {
            request.setAttribute("requestBody", pendingPolicy);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            pendingPolicy = pendingPolicyService.updatePendingPolicy(pendingPolicy);
            pendingPolicyList = new ArrayList<>();
            pendingPolicyList.add(pendingPolicy);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update pendingPolicy");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingPolicyList);
        return pendingPolicyList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePendingPolicies(
            @RequestParam String env,
            @RequestParam(required = false) Long pendingPolicyId,
            @RequestParam(required = false) String pendingPolicyName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(pendingPolicyId != null) {
                pendingPolicyService.deletePendingPolicy(
                        pendingPolicyId);
            } else if (pendingPolicyName != null) {
                pendingPolicyService
                        .deletePendingPolicyByPendingPolicyName(
                                pendingPolicyName);
            } else {
                throw new NotImplementedException("This delete query is not implemented pendingPolicy ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete pendingPolicy");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
