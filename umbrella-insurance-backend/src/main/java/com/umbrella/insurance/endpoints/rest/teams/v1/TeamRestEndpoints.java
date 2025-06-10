package com.umbrella.insurance.endpoints.rest.teams.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.teams.v1.db.TeamPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.teams.v1.db.jpa.TeamService;
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
@RequestMapping(TeamPrivilege.PATH)
public class TeamRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(TeamRestEndpoints.class);

    @Autowired
    private TeamService teamService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Team createTeam(
            @RequestParam String env,
            @RequestBody Team team,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Team teamResponse;
        try {
            request.setAttribute("requestBody", team);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            teamResponse = teamService.saveTeam(team);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create team ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamResponse);
        return teamResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Team> readTeams(
            @RequestParam String env,
            @RequestParam(required = false) Long teamId,
            @RequestParam(required = false) String teamName,
            @RequestParam(required = false) Long levelOfCompetitionId,
            @RequestParam(required = false) Long gameTypeId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Team> teamList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (teamId != null) {
                Optional<Team> team = teamService.getTeamById(teamId);
                if (team.isEmpty()) {
                    throw new NotFoundException("Unable to read team ");
                } else {
                    teamList = new ArrayList<>();
                    teamList.add(team.get());
                }
            } else if (teamName != null && levelOfCompetitionId != null && gameTypeId != null) {
                Optional<Team> team = teamService
                        .getTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
                                teamName,
                                levelOfCompetitionId,
                                gameTypeId);
                if (team.isEmpty()) {
                    throw new NotFoundException("Unable to read team ");
                } else {
                    teamList = new ArrayList<>();
                    teamList.add(team.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented team ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read team ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamList);
        return teamList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Team> updateTeams(
            @RequestParam String env,
            @RequestBody Team team,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Team> teamList;
        try {
            request.setAttribute("requestBody", team);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            teamService.updateTeam(team);
            teamList = new ArrayList<>();
            teamList.add(team);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update team ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", teamList);
        return teamList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTeams(
            @RequestParam String env,
            @RequestParam(required = false) Long teamId,
            @RequestParam(required = false) String teamName,
            @RequestParam(required = false) Long levelOfCompetitionId,
            @RequestParam(required = false) Long gameTypeId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(teamId != null) {
                teamService.deleteTeam(teamId);
            } else if (teamName != null && levelOfCompetitionId != null && gameTypeId != null) {
                teamService
                        .deleteTeamByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
                                teamName, levelOfCompetitionId, gameTypeId);
            } else {
                throw new NotImplementedException("This delete query is not implemented team ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete team ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
