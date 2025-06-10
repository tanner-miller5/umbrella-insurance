package com.umbrella.insurance.endpoints.rest.geographies.islands.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.geographies.islands.v1.db.IslandPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.geographies.islands.v1.db.jpa.IslandService;
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
@RequestMapping(IslandPrivilege.PATH)
public class IslandRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(IslandRestEndpoints.class);

    @Autowired
    private IslandService islandService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Island createIsland(
            @RequestParam String env,
            @RequestBody Island island,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Island islandResponse;
        try {
            request.setAttribute("requestBody", island);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            islandResponse = islandService.saveIsland(island);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create island ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", islandResponse);
        return islandResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Island> readIslands(
            @RequestParam String env,
            @RequestParam(required = false) Long islandId,
            @RequestParam(required = false) String islandName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Island> islandList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (islandId != null) {
                Optional<Island> island = islandService.getIsland(islandId);
                if (island.isEmpty()) {
                    throw new NotFoundException("Unable to read island ");
                } else {
                    islandList = new ArrayList<>();
                    islandList.add(island.get());
                }
            } else if (islandName != null) {
                Optional<Island> island = islandService.getIslandByIslandName(islandName);
                if (island.isEmpty()) {
                    throw new NotFoundException("Unable to read island ");
                } else {
                    islandList = new ArrayList<>();
                    islandList.add(island.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented island ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read island ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", islandList);
        return islandList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Island> updateIslands(
            @RequestParam String env,
            @RequestBody Island island,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Island> islandList;
        try {
            request.setAttribute("requestBody", island);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            islandService.updateIsland(island);
            islandList = new ArrayList<>();
            islandList.add(island);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update island ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", islandList);
        return islandList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteIslands(@RequestParam String env,
                           @RequestParam(required = false) Long islandId,
                           @RequestParam(required = false) String islandName,
                           @RequestAttribute BigInteger currentRequestNumber,
                           ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(islandId != null) {
                islandService.deleteIsland(islandId);
            } else if (islandName != null) {
                islandService.deleteIslandByIslandName(islandName);
            } else {
                throw new NotImplementedException("This delete query is not implemented island ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete island ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
