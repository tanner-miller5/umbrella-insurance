package com.umbrella.insurance.endpoints.rest.users.userAgreements.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.userAgreements.v1.db.UserAgreementPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.userAgreements.v1.db.jpa.UserAgreementService;
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
@RequestMapping(UserAgreementPrivilege.PATH)
public class UserAgreementRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(UserAgreementRestEndpoints.class);

    @Autowired
    private UserAgreementService userAgreementService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UserAgreement createUserAgreement(
            @RequestParam String env,
            @RequestBody UserAgreement userAgreement,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        UserAgreement userAgreementResponse;
        try {
            request.setAttribute("requestBody", userAgreement);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            userAgreementResponse = userAgreementService.saveUserAgreement(userAgreement);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create userAgreement ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", userAgreementResponse);
        return userAgreementResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<UserAgreement> readUserAgreements(
            @RequestParam String env,
            @RequestParam(required = false) Long userAgreementId,
            @RequestParam(required = false) String userAgreementName,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<UserAgreement> userAgreementList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (userAgreementId != null) {
                Optional<UserAgreement> userAgreement = userAgreementService.findUserAgreementByUserAgreementId(
                        userAgreementId);
                if(userAgreement.isEmpty()) {
                    throw new NotFoundException("UserAgreement not found");
                } else {
                    userAgreementList = new ArrayList<>();
                    userAgreementList.add(userAgreement.get());
                }
            } else if (userAgreementName != null && userId != null) {
                Optional<UserAgreement> userAgreement = userAgreementService
                        .getUserAgreementByUserIdAndUserAgreementName(
                                userId, userAgreementName);
                if (userAgreement.isEmpty()) {
                    throw new NotFoundException("Unable to read userAgreement");
                } else {
                    userAgreementList = new ArrayList<>();
                    userAgreementList.add(userAgreement.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented userAgreement");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read userAgreement ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", userAgreementList);
        return userAgreementList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<UserAgreement> updateUserAgreements(
            @RequestParam String env,
            @RequestBody UserAgreement userAgreement,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<UserAgreement> userAgreementList;
        try {
            request.setAttribute("requestBody", userAgreement);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            userAgreement = userAgreementService.updateUserAgreement(
                    userAgreement);
            userAgreementList = new ArrayList<>();
            userAgreementList.add(userAgreement);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update userAgreement ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", userAgreementList);
        return userAgreementList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserAgreements(
            @RequestParam String env,
            @RequestParam(required = false) Long userAgreementId,
            @RequestParam(required = false) String userAgreementName,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(userAgreementId != null) {
                userAgreementService.deleteUserAgreementByUserId(userId);
            } else if (userAgreementName != null) {
                userAgreementService
                        .deleteUserAgreementByUserId(userId);
            } else {
                throw new NotImplementedException("This delete query is not implemented userAgreement ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete userAgreement ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
