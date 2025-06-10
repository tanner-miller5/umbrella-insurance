package com.umbrella.insurance.endpoints.rest.communications.sentEmails.v1;

import com.umbrella.insurance.core.models.communications.sentEmails.v1.db.SentEmailPrivilege;
import com.umbrella.insurance.core.models.communications.sentEmails.v1.db.jpa.SentEmailService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
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

import com.umbrella.insurance.core.models.entities.SentEmail;

@Controller
@RequestMapping(SentEmailPrivilege.PATH)
public class SentEmailRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(SentEmailRestEndpoints.class);

    @Autowired
    private SentEmailService sentEmailService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    SentEmail createSentEmail(
            @RequestParam String env,
            @RequestBody SentEmail sentEmail,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        SentEmail sentEmailResponse;
        try {
            request.setAttribute("requestBody", sentEmail);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            sentEmailResponse = sentEmailService.saveSentEmail(sentEmail);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create sentEmail ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", sentEmailResponse);
        return sentEmailResponse;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<SentEmail> readSentEmails(
            @RequestParam String env,
            @RequestParam(required = false) Long sentEmailId,
            @RequestParam(required = false) String emailSubject,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<SentEmail> sentEmailList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (sentEmailId != null) {
                Optional<SentEmail> sentEmail = sentEmailService.getSentEmailById(sentEmailId);
                if(sentEmail.isEmpty()) {
                    throw new NotFoundException("Unable to find sent email");
                } else {
                    sentEmailList = new ArrayList<>();
                    sentEmailList.add(sentEmail.get());
                }
            } else if (emailSubject != null) {
                Optional<SentEmail> sentEmail = sentEmailService.getSentEmailByEmailSubject(
                        emailSubject);
                if(sentEmail.isEmpty()) {
                    throw new NotFoundException("Unable to find sent email");
                } else {
                    sentEmailList = new ArrayList<>();
                    sentEmailList.add(sentEmail.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented sentEmail ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read sentEmail ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", sentEmailList);
        return sentEmailList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<SentEmail> updateSentEmails(
            @RequestParam String env,
            @RequestBody SentEmail sentEmail,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<SentEmail> sentEmailList;
        try {
            request.setAttribute("requestBody", sentEmail);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            sentEmail = sentEmailService.updateSentEmail(
                    sentEmail);
            sentEmailList = new ArrayList<>();
            sentEmailList.add(sentEmail);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update sentEmail ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", sentEmailList);
        return sentEmailList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSentEmails(@RequestParam String env,
                              @RequestParam(required = false) Long sentEmailId,
                              @RequestParam(required = false) String emailSubject,
                              @RequestAttribute BigInteger currentRequestNumber,
                              ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(sentEmailId != null) {
                sentEmailService.deleteSentEmail(sentEmailId);
            } else if (emailSubject != null) {
                sentEmailService.deleteSentEmailByEmailSubject(emailSubject);
            } else {
                throw new NotImplementedException("This delete query is not implemented sentEmail ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete sentEmail ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
