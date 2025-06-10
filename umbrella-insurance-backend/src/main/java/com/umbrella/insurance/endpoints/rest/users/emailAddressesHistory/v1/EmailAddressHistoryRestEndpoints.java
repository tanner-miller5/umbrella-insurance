package com.umbrella.insurance.endpoints.rest.users.emailAddressesHistory.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.emailAddressesHistory.v1.db.EmailAddressHistoryPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.emailAddressesHistory.v1.db.jpa.EmailAddressHistoryService;
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
@RequestMapping(EmailAddressHistoryPrivilege.PATH)
public class EmailAddressHistoryRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(EmailAddressHistoryRestEndpoints.class);

    @Autowired
    private EmailAddressHistoryService emailAddressHistoryService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    EmailAddressHistory createEmailAddressHistory(
            @RequestParam String env,
            @RequestBody EmailAddressHistory emailAddressHistory,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        EmailAddressHistory emailAddressHistoryResponse;
        try {
            request.setAttribute("requestBody", emailAddressHistory);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            emailAddressHistoryResponse = emailAddressHistoryService.saveEmailAddressHistory(emailAddressHistory);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create emailAddressHistory ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", emailAddressHistoryResponse);
        return emailAddressHistoryResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<EmailAddressHistory> readEmailAddressHistory(
            @RequestParam String env,
            @RequestParam(required = false) Long emailAddressHistoryId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<EmailAddressHistory> emailAddressHistoryList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (emailAddressHistoryId != null) {
                Optional<EmailAddressHistory> emailAddressHistory = emailAddressHistoryService
                        .getEmailAddressHistoryByEmailAddressHistoryId(emailAddressHistoryId);
                if (emailAddressHistory.isEmpty()) {
                    throw new NotFoundException("Unable to read emailAddressHistory ");
                } else {
                    emailAddressHistoryList = new ArrayList<>();
                    emailAddressHistoryList.add(emailAddressHistory.get());
                }
            } else if (userId != null) {
                Optional<EmailAddressHistory> emailAddressHistory = emailAddressHistoryService
                        .getEmailAddressHistoryByUserId(userId);
                if (emailAddressHistory.isEmpty()) {
                    throw new NotFoundException("Unable to read emailAddressHistory ");
                } else {
                    emailAddressHistoryList = new ArrayList<>();
                    emailAddressHistoryList.add(emailAddressHistory.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented emailAddressHistory ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read emailAddressHistory ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", emailAddressHistoryList);
        return emailAddressHistoryList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<EmailAddressHistory> updateEmailAddressHistory(
            @RequestParam String env,
            @RequestBody EmailAddressHistory emailAddressHistory,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<EmailAddressHistory> emailAddressHistoryList;
        try {
            request.setAttribute("requestBody", emailAddressHistory);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            emailAddressHistory = emailAddressHistoryService.updateEmailAddressHistory(
                    emailAddressHistory);
            emailAddressHistoryList = new ArrayList<>();
            emailAddressHistoryList.add(emailAddressHistory);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update emailAddressHistory ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", emailAddressHistoryList);
        return emailAddressHistoryList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEmailAddressHistory(
            @RequestParam String env,
            @RequestParam(required = false) Long emailAddressHistoryId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(emailAddressHistoryId != null) {
                emailAddressHistoryService.deleteEmailAddressHistory(emailAddressHistoryId);
            } else if (userId != null) {
                emailAddressHistoryService.deleteEmailAddressHistoryByUserId(userId);
            } else {
                throw new NotImplementedException("This delete query is not implemented emailAddressHistory ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete emailAddressHistory ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
