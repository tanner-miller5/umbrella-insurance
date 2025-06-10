package com.umbrella.insurance.endpoints.rest.games.gameStatuses.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.games.gameStatuses.v1.db.GameStatusPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.games.gameStatuses.v1.db.jpa.GameStatusService;
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
@RequestMapping(GameStatusPrivilege.PATH)
public class GameStatusRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(GameStatusRestEndpoints.class);

    @Autowired
    private GameStatusService gameStatusService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    GameStatus createGameStatus(
            @RequestParam String env,
            @RequestBody GameStatus gameStatus,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        GameStatus gameStatusResponse;
        try {
            request.setAttribute("requestBody", gameStatus);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            gameStatusResponse = gameStatusService.saveGameStatus(gameStatus);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create gameStatus ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", gameStatusResponse);
        return gameStatusResponse;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<GameStatus> readGameStatuses(
            @RequestParam String env,
            @RequestParam(required = false) Long gameStatusId,
            @RequestParam(required = false) String gameStatusName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<GameStatus> gameStatusList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (gameStatusId != null) {
                Optional<GameStatus> gameStatus = gameStatusService.getGameStatus(
                        gameStatusId);
                if (gameStatus.isEmpty()) {
                    throw new NotFoundException("Unable to read gameStatus ");
                } else {
                    gameStatusList = new ArrayList<>();
                    gameStatusList.add(gameStatus.get());
                }
            } else if (gameStatusName != null) {
                Optional<GameStatus> gameStatus = gameStatusService.getGameStatus(
                        gameStatusName);
                if (gameStatus.isEmpty()) {
                    throw new NotFoundException("Unable to read gameStatus ");
                } else {
                    gameStatusList = new ArrayList<>();
                    gameStatusList.add(gameStatus.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented gameStatus ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read gameStatus ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", gameStatusList);
        return gameStatusList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<GameStatus> updateGameStatuses(
            @RequestParam String env,
            @RequestBody GameStatus gameStatus,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<GameStatus> gameStatusList;
        try {
            request.setAttribute("requestBody", gameStatus);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            gameStatus = gameStatusService.updateGameStatus(
                    gameStatus);
            gameStatusList = new ArrayList<>();
            gameStatusList.add(gameStatus);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update gameStatus ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", gameStatusList);
        return gameStatusList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGameStatuses(@RequestParam String env,
                           @RequestParam(required = false) Long gameStatusId,
                           @RequestParam(required = false) String gameStatusName,
                           @RequestAttribute BigInteger currentRequestNumber,
                           ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(gameStatusId != null) {
                gameStatusService.deleteGameStatus(gameStatusId);
            } else if (gameStatusName != null) {
                gameStatusService.deleteByGameStatusName(
                        gameStatusName);
            } else {
                throw new NotImplementedException("This delete query is not implemented gameStatus ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete gameStatus ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
