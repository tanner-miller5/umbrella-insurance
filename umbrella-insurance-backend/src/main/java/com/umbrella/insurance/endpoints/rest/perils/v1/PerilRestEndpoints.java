package com.umbrella.insurance.endpoints.rest.perils.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.perils.v1.db.PerilPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.perils.v1.db.jpa.PerilService;
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
@RequestMapping(PerilPrivilege.PATH)
public class PerilRestEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(PerilRestEndpoints.class);

    @Autowired
    private PerilService perilService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Peril createPeril(
            @RequestParam String env,
            @RequestBody Peril peril,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Peril perilResponse;
        try {
            request.setAttribute("requestBody", peril);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            perilResponse = perilService.savePeril(peril);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create peril ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", perilResponse);
        return perilResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Peril> readPerils(
            @RequestParam String env,
            @RequestParam(required = false) Long perilId,
            @RequestParam(required = false) String perilName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Peril> perilList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (perilId != null) {
                Optional<Peril> peril = perilService.getPeril(perilId);
                if (peril.isEmpty()) {
                    throw new NotFoundException("Unable to read peril ");
                } else {
                    perilList = new ArrayList<>();
                    perilList.add(peril.get());
                }
            } else if (perilName != null) {
                Optional<Peril> peril = perilService
                        .getPerilByPerilName(perilName);
                if (peril.isEmpty()) {
                    throw new NotFoundException("Unable to read peril ");
                } else {
                    perilList = new ArrayList<>();
                    perilList.add(peril.get());
                }
            } else {
                perilList = perilService.getPerils();
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read peril ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", perilList);
        return perilList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Peril> updatePerils(
            @RequestParam String env,
            @RequestBody Peril peril,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Peril> perilList;
        try {
            request.setAttribute("requestBody", peril);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            peril = perilService
                    .updatePeril(peril);
            perilList = new ArrayList<>();
            perilList.add(peril);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update peril ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", perilList);
        return perilList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePerils(
            @RequestParam String env,
            @RequestParam(required = false) Long perilId,
            @RequestParam(required = false) String perilName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(perilId != null) {
                perilService.deletePeril(perilId);
            } else if (perilName != null) {
                perilService.deletePerilByPerilName(
                                perilName);
            } else {
                throw new NotImplementedException("This delete query is not implemented peril ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete peril ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
