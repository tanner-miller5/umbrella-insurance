package com.umbrella.insurance.endpoints.rest.users.policies.matchedPolicies.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.policies.matchedPolicies.v1.db.MatchedPolicyPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.policies.matchedPolicies.v1.db.jpa.MatchedPolicyService;
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
@RequestMapping(MatchedPolicyPrivilege.PATH)
public class MatchedPolicyRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(MatchedPolicyRestEndpoints.class);

    @Autowired
    private MatchedPolicyService matchedPolicyService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    MatchedPolicy createMatchedPolicy(
            @RequestParam String env,
            @RequestBody MatchedPolicy matchedPolicy,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        MatchedPolicy matchedPolicyResponse;
        try {
            request.setAttribute("requestBody", matchedPolicy);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            matchedPolicyResponse = matchedPolicyService.saveMatchedPolicy(matchedPolicy);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create matchedPolicy ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", matchedPolicyResponse);
        return matchedPolicyResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<MatchedPolicy> readMatchedPolicies(
            @RequestParam String env,
            @RequestParam(required = false) Long matchedPolicyId,
            @RequestParam(required = false) Long pendingInsurerPolicyId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<MatchedPolicy> matchedPolicyList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (matchedPolicyId != null) {
                Optional<MatchedPolicy> matchedPolicy = matchedPolicyService.getMatchedPolicyByMatchedPolicyId(
                        matchedPolicyId);
                if (matchedPolicy.isEmpty()) {
                    throw new NotFoundException("Unable to read matchedPolicy");
                } else {
                    matchedPolicyList = new ArrayList<>();
                    matchedPolicyList.add(matchedPolicy.get());
                }
            } else if (pendingInsurerPolicyId != null) {
                Optional<MatchedPolicy> matchedPolicy = matchedPolicyService
                        .getMatchedPolicyByPendingInsurerPolicyId(
                                pendingInsurerPolicyId);
                if (matchedPolicy.isEmpty()) {
                    throw new NotFoundException("Unable to read matchedPolicy");
                } else {
                    matchedPolicyList = new ArrayList<>();
                    matchedPolicyList.add(matchedPolicy.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented matchedPolicy");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read matchedPolicy ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", matchedPolicyList);
        return matchedPolicyList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<MatchedPolicy> updateMatchedPolicies(
            @RequestParam String env,
            @RequestBody MatchedPolicy matchedPolicy,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<MatchedPolicy> matchedPolicyList;
        try {
            request.setAttribute("requestBody", matchedPolicy);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            matchedPolicy= matchedPolicyService.updateMatchedPolicy(
                    matchedPolicy);
            matchedPolicyList = new ArrayList<>();
            matchedPolicyList.add(matchedPolicy);

        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update matchedPolicy ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", matchedPolicyList);
        return matchedPolicyList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMatchedPolicies(
            @RequestParam String env,
            @RequestParam(required = false) Long matchedPolicyId,
            @RequestParam(required = false) Long pendingInsurerPolicyId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(matchedPolicyId != null) {
                matchedPolicyService.deleteMatchedPolicy(matchedPolicyId);
            } else if (pendingInsurerPolicyId != null) {
                matchedPolicyService
                        .deleteMatchedPolicyByPendingInsurerPolicyId(
                                pendingInsurerPolicyId);
            } else {
                throw new NotImplementedException("This delete query is not implemented matchedPolicy ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete matchedPolicy ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
