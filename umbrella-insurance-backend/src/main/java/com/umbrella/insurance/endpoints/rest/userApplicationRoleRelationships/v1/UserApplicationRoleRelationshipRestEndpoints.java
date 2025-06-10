package com.umbrella.insurance.endpoints.rest.userApplicationRoleRelationships.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.UserApplicationRoleRelationshipPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
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
@RequestMapping(UserApplicationRoleRelationshipPrivilege.PATH)
public class UserApplicationRoleRelationshipRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(UserApplicationRoleRelationshipRestEndpoints.class);

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UserApplicationRoleRelationship createUserApplicationRoleRelationship(
            @RequestParam String env,
            @RequestBody UserApplicationRoleRelationship userApplicationRoleRelationship,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        UserApplicationRoleRelationship userApplicationRoleRelationshipResponse;
        try {
            request.setAttribute("requestBody", userApplicationRoleRelationship);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            userApplicationRoleRelationshipResponse = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                    userApplicationRoleRelationship);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create userApplicationRoleRelationship ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", userApplicationRoleRelationshipResponse);
        return userApplicationRoleRelationshipResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<UserApplicationRoleRelationship> readUserApplicationRoleRelationships(
            @RequestParam String env,
            @RequestParam(required = false) Long userApplicationRoleRelationshipId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long applicationRoleId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<UserApplicationRoleRelationship> userApplicationRoleRelationshipList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (userApplicationRoleRelationshipId != null) {
                Optional<UserApplicationRoleRelationship> userApplicationRoleRelationship = userApplicationRoleRelationshipService
                        .getUserApplicationRoleRelationshipByUserApplicationRoleRelationshipId(
                                userApplicationRoleRelationshipId);
                if (userApplicationRoleRelationship.isEmpty()) {
                    throw new NotFoundException("Unable to read userApplicationRoleRelationship ");
                } else {
                    userApplicationRoleRelationshipList = new ArrayList<>();
                    userApplicationRoleRelationshipList.add(userApplicationRoleRelationship.get());
                }
            } else if (userId != null && applicationRoleId != null) {
                Optional<UserApplicationRoleRelationship> userApplicationRoleRelationship = userApplicationRoleRelationshipService
                        .getUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                                userId, applicationRoleId);
                if (userApplicationRoleRelationship.isEmpty()) {
                    throw new NotFoundException("Unable to read userApplicationRoleRelationship ");
                } else {
                    userApplicationRoleRelationshipList = new ArrayList<>();
                    userApplicationRoleRelationshipList.add(userApplicationRoleRelationship.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented userApplicationRoleRelationship ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read userApplicationRoleRelationship ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", userApplicationRoleRelationshipList);
        return userApplicationRoleRelationshipList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<UserApplicationRoleRelationship> updateUserApplicationRoleRelationships(
            @RequestParam String env,
            @RequestBody UserApplicationRoleRelationship userApplicationRoleRelationship,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<UserApplicationRoleRelationship> userApplicationRoleRelationshipList;
        try {
            request.setAttribute("requestBody", userApplicationRoleRelationship);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            userApplicationRoleRelationship = userApplicationRoleRelationshipService.updateUserApplicationRoleRelationship(
                    userApplicationRoleRelationship);
            userApplicationRoleRelationshipList = new ArrayList<>();
            userApplicationRoleRelationshipList.add(userApplicationRoleRelationship);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update userApplicationRoleRelationship ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", userApplicationRoleRelationshipList);
        return userApplicationRoleRelationshipList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserApplicationRoleRelationships(
            @RequestParam String env,
            @RequestParam(required = false) Long userApplicationRoleRelationshipId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long applicationRoleId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(userApplicationRoleRelationshipId != null) {
                userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationship(
                        userApplicationRoleRelationshipId);
            } else if (userId != null && applicationRoleId != null) {
                userApplicationRoleRelationshipService
                        .deleteUserApplicationRoleRelationshipByUserIdAndApplicationRoleId(
                                userId, applicationRoleId);
            } else {
                throw new NotImplementedException("This delete query is not implemented userApplicationRoleRelationship ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete userApplicationRoleRelationship ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
