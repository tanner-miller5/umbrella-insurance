package com.umbrella.insurance.endpoints.rest.teamTransactions.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.teamTransactions.v1.db.TeamTransactionPrivilege;
import com.umbrella.insurance.core.models.entities.*;

import com.umbrella.insurance.core.models.teamTransactions.v1.db.jpa.TeamTransactionService;
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
@RequestMapping(TeamTransactionPrivilege.PATH)
public class TeamTransactionRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(TeamTransactionRestEndpoints.class);

    @Autowired
    private TeamTransactionService teamTransactionService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    TeamTransaction createTeamTransaction(
            @RequestParam String env,
            @RequestBody TeamTransaction teamTransaction,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        TeamTransaction teamTransactionResponse;
        try {
            request.setAttribute("requestBody", teamTransaction);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            teamTransactionResponse = teamTransactionService.saveTeamTransaction(teamTransaction);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create teamTransaction ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamTransactionResponse);
        return teamTransactionResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TeamTransaction> readTeamTransactions(
            @RequestParam String env,
            @RequestParam(required = false) Long teamTransactionId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long teamId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<TeamTransaction> teamTransactionList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (teamTransactionId != null) {
                Optional<TeamTransaction> teamTransaction = teamTransactionService.getTeamTransactionById(
                        teamTransactionId);
                if (teamTransaction.isEmpty()) {
                    throw new NotFoundException("Unable to read teamTransaction ");
                } else {
                    teamTransactionList = new ArrayList<>();
                    teamTransactionList.add(teamTransaction.get());
                }
            } else if (description != null && teamId != null) {
                Optional<TeamTransaction> teamTransaction = teamTransactionService
                        .getTeamTransactionByTeamTransactionByDescriptionAndTeamId(
                                description,
                                teamId);
                if (teamTransaction.isEmpty()) {
                    throw new NotFoundException("Unable to read teamTransaction ");
                } else {
                    teamTransactionList = new ArrayList<>();
                    teamTransactionList.add(teamTransaction.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented teamTransaction ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read teamTransaction ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamTransactionList);
        return teamTransactionList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TeamTransaction> updateTeamTransactions(
            @RequestParam String env,
            @RequestBody TeamTransaction teamTransaction,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<TeamTransaction> teamTransactionList;
        try {
            request.setAttribute("requestBody", teamTransaction);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            teamTransactionService.updateTeamTransaction(
                    teamTransaction);
            teamTransactionList = new ArrayList<>();
            teamTransactionList.add(teamTransaction);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update teamTransaction ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamTransactionList);
        return teamTransactionList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTeamTransactions(
            @RequestParam String env,
            @RequestParam(required = false) Long teamTransactionId,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long teamId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(teamTransactionId != null) {
                teamTransactionService.deleteTeamTransaction(
                        teamTransactionId);
            } else if (description != null && teamId != null) {
                teamTransactionService
                        .deleteTeamTransactionByDescriptionAndTeamId(
                                description, teamId);
            } else {
                throw new NotImplementedException("This delete query is not implemented teamTransaction ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete teamTransaction ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
