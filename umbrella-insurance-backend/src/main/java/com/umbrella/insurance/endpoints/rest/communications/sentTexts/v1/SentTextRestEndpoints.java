package com.umbrella.insurance.endpoints.rest.communications.sentTexts.v1;

import com.umbrella.insurance.core.models.communications.sentTexts.v1.db.SentTextPrivilege;
import com.umbrella.insurance.core.models.communications.sentTexts.v1.db.jpa.SentTextService;
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

import com.umbrella.insurance.core.models.entities.*;

@Controller
@RequestMapping(SentTextPrivilege.PATH)
public class SentTextRestEndpoints {

    @Autowired
    private SentTextService sentTextService;

    private static final Logger logger = LoggerFactory.getLogger(SentTextRestEndpoints.class);
    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    SentText createSentText(
            @RequestParam String env,
            @RequestBody SentText sentText,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        SentText sentTextResponse;
        try {
            request.setAttribute("requestBody", sentText);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            sentTextResponse = sentTextService.saveSentText(sentText);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create sentText ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", sentTextResponse);
        return sentTextResponse;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<SentText> readSentTexts(
            @RequestParam String env,
            @RequestParam(required = false) Long sentTextId,
            @RequestParam(required = false) String textMessage,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<SentText> sentTextList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (sentTextId != null) {
                Optional<SentText> sentText = sentTextService.getSentTextById(sentTextId);
                if(sentText.isEmpty()) {
                    throw new NotFoundException("Unable to find sent text");
                } else {
                    sentTextList = new ArrayList<>();
                    sentTextList.add(sentText.get());
                }
            } else if (textMessage != null) {
                Optional<SentText> sentText = sentTextService.getSentTextByTextMessage(
                        textMessage);
                if(sentText.isEmpty()) {
                    throw new NotFoundException("Unable to find sent text");
                } else {
                    sentTextList = new ArrayList<>();
                    sentTextList.add(sentText.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented sentText ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read sentText ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", sentTextList);
        return sentTextList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<SentText> updateSentTexts(
            @RequestParam String env,
            @RequestBody SentText sentText,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<SentText> sentTextList;
        try {
            request.setAttribute("requestBody", sentText);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            sentText= sentTextService.updateSentText(
                    sentText);
            sentTextList = new ArrayList<>();
            sentTextList.add(sentText);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update sentText ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", sentTextList);
        return sentTextList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSentTexts(@RequestParam String env,
                                @RequestParam(required = false) Long sentTextId,
                                @RequestParam(required = false) String textMessage,
                                @RequestAttribute BigInteger currentRequestNumber,
                                ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(sentTextId != null) {
                sentTextService.deleteSentText(sentTextId);
            } else if (textMessage != null) {
                sentTextService.deleteByTextMessage(textMessage);
            } else {
                throw new NotImplementedException("This delete query is not implemented sentText ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete sentText ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
