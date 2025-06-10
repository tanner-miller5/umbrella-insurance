package com.umbrella.insurance.endpoints.rest.teamMemberTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.teamMemberTypes.v1.db.TeamMemberTypePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.teamMemberTypes.v1.db.jpa.TeamMemberTypeService;
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
@RequestMapping(TeamMemberTypePrivilege.PATH)
public class TeamMemberTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(TeamMemberTypeRestEndpoints.class);

    @Autowired
    private TeamMemberTypeService teamMemberTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    TeamMemberType createTeamMemberType(
            @RequestParam String env,
            @RequestBody TeamMemberType teamMemberType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        TeamMemberType teamMemberTypeResponse;
        try {
            request.setAttribute("requestBody", teamMemberType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            teamMemberTypeResponse = teamMemberTypeService.saveTeamMemberType(teamMemberType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create teamMemberType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamMemberTypeResponse);
        return teamMemberTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TeamMemberType> readTeamMemberTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long teamMemberTypeId,
            @RequestParam(required = false) String teamMemberTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<TeamMemberType> teamMemberTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (teamMemberTypeId != null) {
                Optional<TeamMemberType> teamMemberType = teamMemberTypeService.getTeamMemberTypeById(
                        teamMemberTypeId);
                if (teamMemberType.isEmpty()) {
                    throw new NotFoundException("Unable to read teamMemberType");
                } else {
                    teamMemberTypeList = new ArrayList<>();
                    teamMemberTypeList.add(teamMemberType.get());
                }
            } else if (teamMemberTypeName != null) {
                Optional<TeamMemberType> teamMemberType = teamMemberTypeService
                        .getTeamMemberTypeByTeamMemberTypeName(teamMemberTypeName);
                if (teamMemberType.isEmpty()) {
                    throw new NotFoundException("Unable to read teamMemberType");
                } else {
                    teamMemberTypeList = new ArrayList<>();
                    teamMemberTypeList.add(teamMemberType.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented teamMemberType");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read teamMemberType");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamMemberTypeList);
        return teamMemberTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TeamMemberType> updateTeamMemberTypes(
            @RequestParam String env,
            @RequestBody TeamMemberType teamMemberType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<TeamMemberType> teamMemberTypeList;
        try {
            request.setAttribute("requestBody", teamMemberType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            teamMemberType = teamMemberTypeService.updateTeamMemberType(
                    teamMemberType);
            teamMemberTypeList = new ArrayList<>();
            teamMemberTypeList.add(teamMemberType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update teamMemberType");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamMemberTypeList);
        return teamMemberTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTeamMemberTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long teamMemberTypeId,
            @RequestParam(required = false) String teamMemberTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(teamMemberTypeId != null) {
                teamMemberTypeService.deleteTeamMemberType(
                        teamMemberTypeId);
            } else if (teamMemberTypeName != null) {
                teamMemberTypeService
                        .deleteTeamMemberTypeByTeamMemberTypeName(
                                teamMemberTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented teamMemberType");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete teamMemberType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
