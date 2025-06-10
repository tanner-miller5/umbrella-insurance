package com.umbrella.insurance.endpoints.rest.users.cardsOnFile.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.cardsOnFile.v1.db.CardOnFilePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.cardsOnFile.v1.db.jpa.CardOnFileService;
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
@RequestMapping(CardOnFilePrivilege.PATH)
public class CardOnFileRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(CardOnFileRestEndpoints.class);

    @Autowired
    private CardOnFileService cardOnFileService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    CardOnFile createCardOnFile(
            @RequestParam String env,
            @RequestBody CardOnFile cardOnFile,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        CardOnFile cardOnFileResponse;
        try {
            request.setAttribute("requestBody", cardOnFile);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            cardOnFileResponse = cardOnFileService.saveCardOnFile(cardOnFile);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create cardOnFile ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cardOnFileResponse);
        return cardOnFileResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<CardOnFile> readCardOnFiles(
            @RequestParam String env,
            @RequestParam(required = false) Long cardOnFileId,
            @RequestParam(required = false) String cardNumber,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<CardOnFile> cardOnFileList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (cardOnFileId != null) {
                Optional<CardOnFile> cardOnFile = cardOnFileService.getCardOnFileByCardOnFileId(
                        cardOnFileId);
                if (cardOnFile.isEmpty()) {
                    throw new NotFoundException("Unable to read cardOnFile ");
                } else {
                    cardOnFileList = new ArrayList<>();
                    cardOnFileList.add(cardOnFile.get());
                }
            } else if (cardNumber != null) {
                Optional<CardOnFile> cardOnFile = cardOnFileService
                        .getCardOnFileByCardNumber(cardNumber);
                if (cardOnFile.isEmpty()) {
                    throw new NotFoundException("Unable to read cardOnFile ");
                } else {
                    cardOnFileList = new ArrayList<>();
                    cardOnFileList.add(cardOnFile.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented cardOnFile ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read cardOnFile ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cardOnFileList);
        return cardOnFileList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<CardOnFile> updateCardOnFiles(
            @RequestParam String env,
            @RequestBody CardOnFile cardOnFile,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<CardOnFile> cardOnFileList;
        try {
            request.setAttribute("requestBody", cardOnFile);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            cardOnFile = cardOnFileService.updateCardOnFile(
                    cardOnFile);
            cardOnFileList = new ArrayList<>();
            cardOnFileList.add(cardOnFile);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update cardOnFile ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cardOnFileList);
        return cardOnFileList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCardOnFiles(
            @RequestParam String env,
            @RequestParam(required = false) Long cardOnFileId,
            @RequestParam(required = false) String cardNumber,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(cardOnFileId != null) {
                cardOnFileService.deleteCardOnFile(
                        cardOnFileId);
            } else if (cardNumber != null) {
                cardOnFileService
                        .deleteCardOnFileByCardNumber(cardNumber);
            } else {
                throw new NotImplementedException("This delete query is not implemented cardOnFile ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete cardOnFile ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
