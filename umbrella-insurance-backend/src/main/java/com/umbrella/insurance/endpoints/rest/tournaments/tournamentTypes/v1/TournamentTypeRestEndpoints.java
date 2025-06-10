package com.umbrella.insurance.endpoints.rest.tournaments.tournamentTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.tournaments.tournamentTypes.v1.db.TournamentTypePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.tournaments.tournamentTypes.v1.db.jpa.TournamentTypeService;
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
@RequestMapping(TournamentTypePrivilege.PATH)
public class TournamentTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(TournamentTypeRestEndpoints.class);

    @Autowired
    private TournamentTypeService tournamentTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    TournamentType createTournamentType(
            @RequestParam String env,
            @RequestBody TournamentType tournamentType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        TournamentType tournamentTypeResponse;
        try {
            request.setAttribute("requestBody", tournamentType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            tournamentTypeResponse = tournamentTypeService.saveTournamentType(tournamentType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create tournamentType");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", tournamentTypeResponse);
        return tournamentTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TournamentType> readTournamentTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long tournamentTypeId,
            @RequestParam(required = false) String tournamentTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<TournamentType> tournamentTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (tournamentTypeId != null) {
                Optional<TournamentType> tournamentType = tournamentTypeService.findTournamentTypeById(
                        tournamentTypeId);
                if (tournamentType.isEmpty()) {
                    throw new NotFoundException("Unable to read tournamentType ");
                } else {
                    tournamentTypeList = new ArrayList<>();
                    tournamentTypeList.add(tournamentType.get());
                }
            } else if (tournamentTypeName != null) {
                Optional<TournamentType> tournamentType = tournamentTypeService
                        .findTournamentTypeByTournamentTypeName(tournamentTypeName);
                if (tournamentType.isEmpty()) {
                    throw new NotFoundException("Unable to read tournamentType ");
                } else {
                    tournamentTypeList = new ArrayList<>();
                    tournamentTypeList.add(tournamentType.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented tournamentType ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read tournamentType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", tournamentTypeList);
        return tournamentTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TournamentType> updateTournamentTypes(
            @RequestParam String env,
            @RequestBody TournamentType tournamentType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<TournamentType> tournamentTypeList;
        try {
            request.setAttribute("requestBody", tournamentType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            tournamentType = tournamentTypeService.updateTournamentType(
                        tournamentType);
            tournamentTypeList = new ArrayList<>();
            tournamentTypeList.add(tournamentType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update tournamentType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", tournamentTypeList);
        return tournamentTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTournamentTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long tournamentTypeId,
            @RequestParam(required = false) String tournamentTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(tournamentTypeId != null) {
                tournamentTypeService.deleteTournamentType(
                        tournamentTypeId);
            } else if (tournamentTypeName != null) {
                tournamentTypeService
                        .deleteTournamentTypeByTournamentTypeName(
                                tournamentTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented tournamentType ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete tournamentType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
