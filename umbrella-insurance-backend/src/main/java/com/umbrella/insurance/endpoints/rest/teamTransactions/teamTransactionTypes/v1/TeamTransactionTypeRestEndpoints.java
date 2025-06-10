package com.umbrella.insurance.endpoints.rest.teamTransactions.teamTransactionTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.teamTransactions.teamTransactionTypes.v1.db.TeamTransactionTypePrivilege;
import com.umbrella.insurance.core.models.teamTransactions.teamTransactionTypes.v1.db.jpa.TeamTransactionTypeService;
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
@RequestMapping(TeamTransactionTypePrivilege.PATH)
public class TeamTransactionTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(TeamTransactionTypeRestEndpoints.class);

    @Autowired
    private TeamTransactionTypeService teamTransactionTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    TeamTransactionType createTeamTransactionType(
            @RequestParam String env,
            @RequestBody TeamTransactionType teamTransactionType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        TeamTransactionType teamTransactionTypeResponse;
        try {
            request.setAttribute("requestBody", teamTransactionType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            teamTransactionTypeResponse = teamTransactionTypeService.saveTeamTransactionType(teamTransactionType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create teamTransactionType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamTransactionTypeResponse);
        return teamTransactionTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TeamTransactionType> readTeamTransactionTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long teamTransactionTypeId,
            @RequestParam(required = false) String teamTransactionTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<TeamTransactionType> teamTransactionTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (teamTransactionTypeId != null) {
                Optional<TeamTransactionType> teamTransactionType = teamTransactionTypeService.findTeamTransactionTypeById(
                        teamTransactionTypeId);
                if (teamTransactionType.isEmpty()) {
                    throw new NotFoundException("Unable to read teamTransactionType ");
                } else {
                    teamTransactionTypeList = new ArrayList<>();
                    teamTransactionTypeList.add(teamTransactionType.get());
                }
            } else if (teamTransactionTypeName != null) {
                Optional<TeamTransactionType> teamTransactionType = teamTransactionTypeService
                        .findTeamTransactionTypeByTeamTransactionTypeName(teamTransactionTypeName);
                if (teamTransactionType.isEmpty()) {
                    throw new NotFoundException("Unable to read teamTransactionType ");
                } else {
                    teamTransactionTypeList = new ArrayList<>();
                    teamTransactionTypeList.add(teamTransactionType.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented teamTransactionType ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read teamTransactionType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamTransactionTypeList);
        return teamTransactionTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TeamTransactionType> updateTeamTransactionTypes(
            @RequestParam String env,
            @RequestBody TeamTransactionType teamTransactionType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<TeamTransactionType> teamTransactionTypeList;
        try {
            request.setAttribute("requestBody", teamTransactionType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            teamTransactionTypeService.updateTeamTransactionType(teamTransactionType);
            teamTransactionTypeList = new ArrayList<>();
            teamTransactionTypeList.add(teamTransactionType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update teamTransactionType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamTransactionTypeList);
        return teamTransactionTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTeamTransactionTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long teamTransactionTypeId,
            @RequestParam(required = false) String teamTransactionTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(teamTransactionTypeId != null) {
                teamTransactionTypeService.deleteTeamTransactionType(
                        teamTransactionTypeId);
            } else if (teamTransactionTypeName != null) {
                teamTransactionTypeService
                        .deleteTeamTransactionTypeByTeamTransactionTypeName(
                                teamTransactionTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented teamTransactionType ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete teamTransactionType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
