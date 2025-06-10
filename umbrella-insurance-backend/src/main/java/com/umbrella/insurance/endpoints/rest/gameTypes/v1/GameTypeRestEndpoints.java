package com.umbrella.insurance.endpoints.rest.gameTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.gameTypes.v1.db.GameTypePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.gameTypes.v1.db.jpa.GameTypeService;
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
@RequestMapping(GameTypePrivilege.PATH)
public class GameTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(GameTypeRestEndpoints.class);

    @Autowired
    private GameTypeService gameTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    GameType createGameType(
            @RequestParam String env,
            @RequestBody GameType gameType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        GameType gameTypeResponse;
        try {
            request.setAttribute("requestBody", gameType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            gameTypeResponse = gameTypeService.saveGameType(gameType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create gameType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", gameTypeResponse);
        return gameTypeResponse;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<GameType> readGameTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long gameTypeId,
            @RequestParam(required = false) String gameTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<GameType> gameTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (gameTypeId != null) {
                Optional<GameType> gameType = gameTypeService.getGameTypeByGameId(gameTypeId);
                if (gameType.isEmpty()) {
                    throw new NotFoundException("Unable to read gameType ");
                } else {
                    gameTypeList = new ArrayList<>();
                    gameTypeList.add(gameType.get());
                }
            } else if (gameTypeName != null) {
                Optional<GameType> gameType = gameTypeService.getGameTypeByGameTypeName(
                        gameTypeName);
                if (gameType.isEmpty()) {
                    throw new NotFoundException("Unable to read gameType ");
                } else {
                    gameTypeList = new ArrayList<>();
                    gameTypeList.add(gameType.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented gameType ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read gameType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", gameTypeList);
        return gameTypeList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<GameType> updateGameTypes(
            @RequestParam String env,
            @RequestBody GameType gameType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<GameType> gameTypeList;
        try {
            request.setAttribute("requestBody", gameType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            gameType = gameTypeService.updateGameType(
                    gameType);
            gameTypeList = new ArrayList<>();
            gameTypeList.add(gameType);

        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update gameType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", gameTypeList);
        return gameTypeList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGameTypes(@RequestParam String env,
                                 @RequestParam(required = false) Long gameTypeId,
                                 @RequestParam(required = false) String gameTypeName,
                                 @RequestAttribute BigInteger currentRequestNumber,
                                 ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(gameTypeId != null) {
                gameTypeService.deleteGameType(gameTypeId);
            } else if (gameTypeName != null) {
                gameTypeService.deleteByGameTypeName(gameTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented gameType ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete gameType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
