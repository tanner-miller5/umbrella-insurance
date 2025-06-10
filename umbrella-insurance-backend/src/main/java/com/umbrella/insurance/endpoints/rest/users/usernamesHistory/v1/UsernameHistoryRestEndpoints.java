package com.umbrella.insurance.endpoints.rest.users.usernamesHistory.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.usernamesHistory.v1.db.UsernameHistoryPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.usernamesHistory.v1.db.jpa.UsernameHistoryService;
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
@RequestMapping(UsernameHistoryPrivilege.PATH)
public class UsernameHistoryRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(UsernameHistoryRestEndpoints.class);

    @Autowired
    private UsernameHistoryService usernameHistoryService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UsernameHistory createUsernameHistory(
            @RequestParam String env,
            @RequestBody UsernameHistory usernameHistory,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        UsernameHistory usernameHistoryResponse;
        try {
            request.setAttribute("requestBody", usernameHistory);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            usernameHistoryResponse = usernameHistoryService.saveUsernameHistory(usernameHistory);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create usernameHistory");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", usernameHistoryResponse);
        return usernameHistoryResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<UsernameHistory> readUsernameHistory(
            @RequestParam String env,
            @RequestParam(required = false) Long usernameHistoryId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<UsernameHistory> usernameHistoryList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (usernameHistoryId != null) {
                Optional<UsernameHistory> usernameHistory = usernameHistoryService
                        .getUsernameHistoryByUsernameHistoryId(usernameHistoryId);
                if (usernameHistory.isEmpty()) {
                    throw new NotFoundException("Unable to read usernameHistory ");
                } else {
                    usernameHistoryList = new ArrayList<>();
                    usernameHistoryList.add(usernameHistory.get());
                }
            } else if (userId != null) {
                Optional<UsernameHistory> usernameHistory = usernameHistoryService
                        .getUsernameHistoryByUserId(userId);
                if (usernameHistory.isEmpty()) {
                    throw new NotFoundException("Unable to read usernameHistory ");
                } else {
                    usernameHistoryList = new ArrayList<>();
                    usernameHistoryList.add(usernameHistory.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented usernameHistory ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read usernameHistory");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", usernameHistoryList);
        return usernameHistoryList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<UsernameHistory> updateUsernameHistory(
            @RequestParam String env,
            @RequestBody UsernameHistory usernameHistory,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<UsernameHistory> usernameHistoryList;
        try {
            request.setAttribute("requestBody", usernameHistory);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            usernameHistory = usernameHistoryService.updateUsernameHistory(usernameHistory);
            usernameHistoryList = new ArrayList<>();
            usernameHistoryList.add(usernameHistory);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update usernameHistory");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", usernameHistoryList);
        return usernameHistoryList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUsernameHistory(
            @RequestParam String env,
            @RequestParam(required = false) Long usernameHistoryId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(usernameHistoryId != null) {
                usernameHistoryService.deleteUsernameHistory(usernameHistoryId);
            } else if (userId != null) {
                usernameHistoryService.deleteUsernameHistoryByUserId(userId);
            } else {
                throw new NotImplementedException("This delete query is not implemented usernameHistory ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete usernameHistory");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
