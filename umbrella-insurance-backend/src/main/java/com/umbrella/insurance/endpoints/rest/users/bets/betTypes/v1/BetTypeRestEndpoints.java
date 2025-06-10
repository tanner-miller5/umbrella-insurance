package com.umbrella.insurance.endpoints.rest.users.bets.betTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.bets.betTypes.v1.db.BetTypePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.bets.betTypes.v1.db.jpa.BetTypeService;
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
@RequestMapping(BetTypePrivilege.PATH)
public class BetTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(BetTypeRestEndpoints.class);

    @Autowired
    private BetTypeService betTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    BetType createBetType(
            @RequestParam String env,
            @RequestBody BetType betType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        BetType betTypeResponse;
        try {
            request.setAttribute("requestBody", betType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            betTypeResponse = betTypeService.saveBetType(betType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create betType");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", betTypeResponse);
        return betTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<BetType> readBetTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long betTypeId,
            @RequestParam(required = false) String betTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<BetType> betTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (betTypeId != null) {
                Optional<BetType> betType = betTypeService.getBetTypeByBetTypeId(
                        betTypeId);
                if (betType.isEmpty()) {
                    throw new NotFoundException("Unable to read betType ");
                } else {
                    betTypeList = new ArrayList<>();
                    betTypeList.add(betType.get());
                }
            } else if (betTypeName != null) {
                Optional<BetType> betType = betTypeService
                        .getBetTypeByBetTypeName(betTypeName);
                if (betType.isEmpty()) {
                    throw new NotFoundException("Unable to read betType ");
                } else {
                    betTypeList = new ArrayList<>();
                    betTypeList.add(betType.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented betType ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read betType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", betTypeList);
        return betTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<BetType> updateBetTypes(
            @RequestParam String env,
            @RequestBody BetType betType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<BetType> betTypeList;
        try {
            request.setAttribute("requestBody", betType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            betType = betTypeService.updateBetType(betType);
            betTypeList = new ArrayList<>();
            betTypeList.add(betType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update betType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", betTypeList);
        return betTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBetTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long betTypeId,
            @RequestParam(required = false) String betTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(betTypeId != null) {
                betTypeService.deleteBetType(
                        betTypeId);
            } else if (betTypeName != null) {
                betTypeService
                        .deleteBetTypeByBetTypeName(
                                betTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented betType ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete betType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
