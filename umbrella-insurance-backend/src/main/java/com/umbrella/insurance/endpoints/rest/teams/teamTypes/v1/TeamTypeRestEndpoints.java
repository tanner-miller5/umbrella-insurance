package com.umbrella.insurance.endpoints.rest.teams.teamTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.teams.teamTypes.v1.db.TeamTypePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.teams.teamTypes.v1.db.jpa.TeamTypeService;
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
@RequestMapping(TeamTypePrivilege.PATH)
public class TeamTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(TeamTypeRestEndpoints.class);

    @Autowired
    private TeamTypeService teamTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    TeamType createTeamType(
            @RequestParam String env,
            @RequestBody TeamType teamType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        TeamType teamTypeResponse;
        try {
            request.setAttribute("requestBody", teamType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            teamTypeResponse = teamTypeService.saveTeamType(teamType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create teamType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamTypeResponse);
        return teamTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TeamType> readTeamTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long teamTypeId,
            @RequestParam(required = false) String teamTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<TeamType> teamTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (teamTypeId != null) {
                Optional<TeamType> teamType = teamTypeService.getTeamTypeById(
                        teamTypeId);
                if (teamType.isEmpty()) {
                    throw new NotFoundException("Unable to read teamType ");
                } else {
                    teamTypeList = new ArrayList<>();
                    teamTypeList.add(teamType.get());
                }
            } else if (teamTypeName != null) {
                Optional<TeamType> teamType = teamTypeService
                        .getTeamTypeByTeamTypeName(teamTypeName);
                if (teamType.isEmpty()) {
                    throw new NotFoundException("Unable to read teamType ");
                } else {
                    teamTypeList = new ArrayList<>();
                    teamTypeList.add(teamType.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented teamType");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read teamType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamTypeList);
        return teamTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TeamType> updateTeamTypes(
            @RequestParam String env,
            @RequestBody TeamType teamType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<TeamType> teamTypeList;
        try {
            request.setAttribute("requestBody", teamType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            teamType = teamTypeService.updateTeamType(
                    teamType);
            teamTypeList = new ArrayList<>();
            teamTypeList.add(teamType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update teamType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamTypeList);
        return teamTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTeamTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long teamTypeId,
            @RequestParam(required = false) String teamTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(teamTypeId != null) {
                teamTypeService.deleteTeamType(teamTypeId);
            } else if (teamTypeName != null) {
                teamTypeService
                        .deleteTeamTypeByTeamTypeName(
                                teamTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented teamType ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete teamType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
