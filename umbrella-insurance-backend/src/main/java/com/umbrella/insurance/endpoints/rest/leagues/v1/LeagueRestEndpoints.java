package com.umbrella.insurance.endpoints.rest.leagues.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.leagues.v1.db.LeaguePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.leagues.v1.db.jpa.LeagueService;
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
@RequestMapping(LeaguePrivilege.PATH)
public class LeagueRestEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(LeagueRestEndpoints.class);

    @Autowired
    private LeagueService leagueService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    League createLeague(
            @RequestParam String env,
            @RequestBody League league,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        League leagueResponse;
        try {
            request.setAttribute("requestBody", league);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            leagueResponse = leagueService.saveLeague(league);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create league ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", leagueResponse);
        return leagueResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<League> readLeagues(
            @RequestParam String env,
            @RequestParam(required = false) Long leagueId,
            @RequestParam(required = false) String leagueName,
            @RequestParam(required = false) Long gameTypeId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<League> leagueList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (leagueId != null) {
                Optional<League> league = leagueService.getLeague(leagueId);
                if (league.isEmpty()) {
                    throw new NotFoundException("Unable to read league ");
                } else {
                    leagueList = new ArrayList<>();
                    leagueList.add(league.get());
                }
            } else if (leagueName != null) {
                Optional<League> league = leagueService.getLeagueByLeagueNameAndGameTypeId(
                                leagueName,
                                gameTypeId);
                if (league.isEmpty()) {
                    throw new NotFoundException("Unable to read league ");
                } else {
                    leagueList = new ArrayList<>();
                    leagueList.add(league.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented league ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read league ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", leagueList);
        return leagueList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<League> updateLeagues(
            @RequestParam String env,
            @RequestBody League league,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<League> leagueList;
        try {
            request.setAttribute("requestBody", league);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            league = leagueService.updateLeague(league);
            leagueList = new ArrayList<>();
            leagueList.add(league);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update league ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", leagueList);
        return leagueList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteLeagues(
            @RequestParam String env,
            @RequestParam(required = false) Long leagueId,
            @RequestParam(required = false) String leagueName,
            @RequestParam(required = false) Long gameTypeId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(leagueId != null) {
                leagueService.deleteLeague(leagueId);
            } else if (leagueName != null) {
                leagueService.deleteLeagueByLeagueNameAndGameTypeId(
                                leagueName, gameTypeId);
            } else {
                throw new NotImplementedException("This delete query is not implemented league ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete league ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
