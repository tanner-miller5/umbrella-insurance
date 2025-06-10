package com.umbrella.insurance.endpoints.rest.users.bets.pendingBets.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.bets.pendingBets.v1.db.PendingBetPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.bets.pendingBets.v1.db.jpa.PendingBetService;
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
@RequestMapping(PendingBetPrivilege.PATH)
public class PendingBetRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(PendingBetRestEndpoints.class);

    @Autowired
    private PendingBetService pendingBetService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PendingBet createPendingBet(
            @RequestParam String env,
            @RequestBody PendingBet pendingBet,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        PendingBet pendingBetResponse;
        try {
            request.setAttribute("requestBody", pendingBet);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            pendingBetResponse = pendingBetService.savePendingBet(pendingBet);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create pendingBet ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingBetResponse);
        return pendingBetResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PendingBet> readPendingBets(
            @RequestParam String env,
            @RequestParam(required = false) Long pendingBetId,
            @RequestParam(required = false) String pendingBetName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PendingBet> pendingBetList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (pendingBetId != null) {
                Optional<PendingBet> pendingBet = pendingBetService.getPendingBetByPendingBetId(
                        pendingBetId);
                if (pendingBet.isEmpty()) {
                    throw new NotFoundException("Unable to read pendingBet");
                } else {
                    pendingBetList = new ArrayList<>();
                    pendingBetList.add(pendingBet.get());
                }
            } else if (pendingBetName != null) {
                Optional<PendingBet> pendingBet = pendingBetService
                        .getPendingBetByPendingBetName(pendingBetName);
                if (pendingBet.isEmpty()) {
                    throw new NotFoundException("Unable to read pendingBet");
                } else {
                    pendingBetList = new ArrayList<>();
                    pendingBetList.add(pendingBet.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented pendingBet");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read pendingBet");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingBetList);
        return pendingBetList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PendingBet> updatePendingBets(
            @RequestParam String env,
            @RequestBody PendingBet pendingBet,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PendingBet> pendingBetList;
        try {
            request.setAttribute("requestBody", pendingBet);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            pendingBet = pendingBetService.updatePendingBet(
                    pendingBet);
            pendingBetList = new ArrayList<>();
            pendingBetList.add(pendingBet);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update pendingBet ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", pendingBetList);
        return pendingBetList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePendingBets(
            @RequestParam String env,
            @RequestParam(required = false) Long pendingBetId,
            @RequestParam(required = false) String pendingBetName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(pendingBetId != null) {
                pendingBetService.deletePendingBet(
                        pendingBetId);
            } else if (pendingBetName != null) {
                pendingBetService
                        .deletePendingBetByPendingBetName(
                                pendingBetName);
            } else {
                throw new NotImplementedException("This delete query is not implemented pendingBet ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete pendingBet ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
