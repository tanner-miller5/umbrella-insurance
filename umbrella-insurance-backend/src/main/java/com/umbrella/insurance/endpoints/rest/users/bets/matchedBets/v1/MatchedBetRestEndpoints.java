package com.umbrella.insurance.endpoints.rest.users.bets.matchedBets.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.bets.matchedBets.v1.db.MatchedBetPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.bets.matchedBets.v1.db.jpa.MatchedBetService;
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
@RequestMapping(MatchedBetPrivilege.PATH)
public class MatchedBetRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(MatchedBetRestEndpoints.class);

    @Autowired
    private MatchedBetService matchedBetService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    MatchedBet createMatchedBet(
            @RequestParam String env,
            @RequestBody MatchedBet matchedBet,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        MatchedBet matchedBetResponse;
        try {
            request.setAttribute("requestBody", matchedBet);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            matchedBetResponse = matchedBetService.saveMatchedBet(matchedBet);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create matchedBet ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", matchedBetResponse);
        return matchedBetResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<MatchedBet> readMatchedBets(
            @RequestParam String env,
            @RequestParam(required = false) Long matchedBetId,
            @RequestParam(required = false) Long pendingBetId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<MatchedBet> matchedBetList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (matchedBetId != null) {
                Optional<MatchedBet> matchedBet = matchedBetService.getMatchedBetByMatchedBetId(
                        matchedBetId);
                if (matchedBet.isEmpty()) {
                    throw new NotFoundException("Unable to read matchedBet ");
                } else {
                    matchedBetList = new ArrayList<>();
                    matchedBetList.add(matchedBet.get());
                }
            } else if (pendingBetId != null) {
                Optional<MatchedBet> matchedBet = matchedBetService
                        .getMatchedBetByPendingBetId1(pendingBetId);
                if (matchedBet.isEmpty()) {
                    throw new NotFoundException("Unable to read matchedBet ");
                } else {
                    matchedBetList = new ArrayList<>();
                    matchedBetList.add(matchedBet.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented matchedBet ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read matchedBet ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", matchedBetList);
        return matchedBetList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<MatchedBet> updateMatchedBets(
            @RequestParam String env,
            @RequestBody MatchedBet matchedBet,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<MatchedBet> matchedBetList;
        try {
            request.setAttribute("requestBody", matchedBet);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            matchedBet = matchedBetService.updateMatchedBet(
                    matchedBet);
            matchedBetList = new ArrayList<>();
            matchedBetList.add(matchedBet);

        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update matchedBet ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", matchedBetList);
        return matchedBetList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMatchedBets(
            @RequestParam String env,
            @RequestParam(required = false) Long matchedBetId,
            @RequestParam(required = false) Long pendingBetId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(matchedBetId != null) {
                matchedBetService.deleteMatchedBet(
                        matchedBetId);
            } else if (pendingBetId != null) {
               matchedBetService
                        .deleteMatchedBetByPendingBetId(
                                pendingBetId);
            } else {
                throw new NotImplementedException("This delete query is not implemented matchedBet ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete matchedBet ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
