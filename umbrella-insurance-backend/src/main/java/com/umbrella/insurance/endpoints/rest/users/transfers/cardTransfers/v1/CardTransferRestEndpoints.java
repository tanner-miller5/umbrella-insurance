package com.umbrella.insurance.endpoints.rest.users.transfers.cardTransfers.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.transfers.cardTransfers.v1.db.CardTransferPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.transfers.cardTransfers.v1.db.jpa.CardTransferService;
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
@RequestMapping(CardTransferPrivilege.PATH)
public class CardTransferRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(CardTransferRestEndpoints.class);

    @Autowired
    private CardTransferService cardTransferService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    CardTransfer createCardTransfer(
            @RequestParam String env,
            @RequestBody CardTransfer cardTransfer,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        CardTransfer cardTransferResponse;
        try {
            request.setAttribute("requestBody", cardTransfer);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            cardTransferResponse = cardTransferService.saveCardTransfer(cardTransfer);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create cardTransfer ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cardTransferResponse);
        return cardTransferResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<CardTransfer> readCardTransfers(
            @RequestParam String env,
            @RequestParam(required = false) Long cardTransferId,
            @RequestParam(required = false) Long transferId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<CardTransfer> cardTransferList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (cardTransferId != null) {
                Optional<CardTransfer> cardTransfer = cardTransferService.getCardTransferByCardTransferId(
                        cardTransferId);
                if (cardTransfer.isEmpty()) {
                    throw new NotFoundException("Unable to read cardTransfer ");
                } else {
                    cardTransferList = new ArrayList<>();
                    cardTransferList.add(cardTransfer.get());
                }
            } else if (transferId != null) {
                Optional<CardTransfer> cardTransfer = cardTransferService
                        .getCardTransferByTransferId(transferId);
                if (cardTransfer.isEmpty()) {
                    throw new NotFoundException("Unable to read cardTransfer ");
                } else {
                    cardTransferList = new ArrayList<>();
                    cardTransferList.add(cardTransfer.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented cardTransfer ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read cardTransfer ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cardTransferList);
        return cardTransferList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<CardTransfer> updateCardTransfers(
            @RequestParam String env,
            @RequestBody CardTransfer cardTransfer,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<CardTransfer> cardTransferList;
        try {
            request.setAttribute("requestBody", cardTransfer);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            cardTransfer = cardTransferService.updateCardTransfer(cardTransfer);
            cardTransferList = new ArrayList<>();
            cardTransferList.add(cardTransfer);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update cardTransfer ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cardTransferList);
        return cardTransferList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCardTransfers(
            @RequestParam String env,
            @RequestParam(required = false) Long cardTransferId,
            @RequestParam(required = false) Long transferId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(cardTransferId != null) {
                cardTransferService.deleteCardTransfer(cardTransferId);
            } else if (transferId != null) {
                cardTransferService.deleteCardTransferByTransferId(transferId);
            } else {
                throw new NotImplementedException("This delete query is not implemented cardTransfer ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete cardTransfer ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
