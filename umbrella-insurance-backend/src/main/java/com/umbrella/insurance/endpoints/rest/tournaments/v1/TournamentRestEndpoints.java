package com.umbrella.insurance.endpoints.rest.tournaments.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.tournaments.v1.db.TournamentPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.tournaments.v1.db.jpa.TournamentService;
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
@RequestMapping(TournamentPrivilege.PATH)
public class TournamentRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(TournamentRestEndpoints.class);

    @Autowired
    private TournamentService tournamentService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Tournament createTournament(
            @RequestParam String env,
            @RequestBody Tournament tournament,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Tournament tournamentResponse;
        try {
            request.setAttribute("requestBody", tournament);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            tournamentResponse = tournamentService.saveTournament(tournament);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create tournament ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", tournamentResponse);
        return tournamentResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Tournament> readTournaments(
            @RequestParam String env,
            @RequestParam(required = false) Long tournamentId,
            @RequestParam(required = false) String tournamentName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Tournament> tournamentList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (tournamentId != null) {
                Optional<Tournament> tournament = tournamentService.getTournamentById(
                        tournamentId);
                if (tournament.isEmpty()) {
                    throw new NotFoundException("Unable to read tournament ");
                } else {
                    tournamentList = new ArrayList<>();
                    tournamentList.add(tournament.get());
                }
            } else if (tournamentName != null) {
                Optional<Tournament> tournament = tournamentService
                        .getTournamentByTournamentName(tournamentName);
                if (tournament.isEmpty()) {
                    throw new NotFoundException("Unable to read tournament ");
                } else {
                    tournamentList = new ArrayList<>();
                    tournamentList.add(tournament.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented tournament ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read tournament ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", tournamentList);
        return tournamentList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Tournament> updateTournaments(
            @RequestParam String env,
            @RequestBody Tournament tournament,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Tournament> tournamentList;
        try {
            request.setAttribute("requestBody", tournament);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            tournament = tournamentService.updateTournament(
                    tournament);
            tournamentList = new ArrayList<>();
            tournamentList.add(tournament);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update tournament ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", tournamentList);
        return tournamentList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTournaments(
            @RequestParam String env,
            @RequestParam(required = false) Long tournamentId,
            @RequestParam(required = false) String tournamentName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(tournamentId != null) {
                tournamentService.deleteTournament(
                        tournamentId);
            } else if (tournamentName != null) {
                tournamentService
                        .deleteTournamentByTournamentName(
                                tournamentName);
            } else {
                throw new NotImplementedException("This delete query is not implemented tournament ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete tournament ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
