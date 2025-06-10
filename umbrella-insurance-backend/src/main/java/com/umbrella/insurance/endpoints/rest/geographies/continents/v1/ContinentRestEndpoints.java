package com.umbrella.insurance.endpoints.rest.geographies.continents.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.geographies.continents.v1.db.ContinentPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.geographies.continents.v1.db.jpa.ContinentService;
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
@RequestMapping(ContinentPrivilege.PATH)
public class ContinentRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(ContinentRestEndpoints.class);

    @Autowired
    private ContinentService continentService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Continent createContinent(
            @RequestParam String env,
            @RequestBody Continent continent,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Continent continentResponse;
        try {
            request.setAttribute("requestBody", continent);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            continentResponse = continentService.saveContinent(continent);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create continent ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", continentResponse);
        return continentResponse;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Continent> readContinents(
            @RequestParam String env,
            @RequestParam(required = false) Long continentId,
            @RequestParam(required = false) String continentName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Continent> continentList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (continentId != null) {
                Optional<Continent> continent = continentService.getContinentByContinentId(
                        continentId);
                if (continent.isEmpty()) {
                    throw new NotFoundException("Unable to read continent ");
                } else {
                    continentList = new ArrayList<>();
                    continentList.add(continent.get());
                }
            } else if (continentName != null) {
                Optional<Continent> continent = continentService.getContinentByContinentName(
                        continentName);
                if (continent.isEmpty()) {
                    throw new NotFoundException("Unable to read continent ");
                } else {
                    continentList = new ArrayList<>();
                    continentList.add(continent.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented continent ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read continent ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", continentList);
        return continentList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Continent> updateContinents(
            @RequestParam String env,
            @RequestBody Continent continent,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Continent> continentList;
        try {
            request.setAttribute("requestBody", continent);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            continent = continentService.updateContinent(
                    continent);
            continentList = new ArrayList<>();
            continentList.add(continent);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update continent ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", continentList);
        return continentList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteContinents(@RequestParam String env,
                           @RequestParam(required = false) Long continentId,
                           @RequestParam(required = false) String continentName,
                           @RequestAttribute BigInteger currentRequestNumber,
                           ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(continentId != null) {
                continentService.deleteContinent(continentId);
            } else if (continentName != null) {
                continentService.deleteContinentByContinentName(
                        continentName);
            } else {
                throw new NotImplementedException("This delete query is not implemented continent ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete continent ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
