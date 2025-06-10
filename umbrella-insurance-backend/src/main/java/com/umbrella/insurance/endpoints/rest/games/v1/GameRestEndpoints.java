package com.umbrella.insurance.endpoints.rest.games.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.games.v1.db.GamePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.games.v1.db.jpa.GameService;
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
@RequestMapping(GamePrivilege.PATH)
public class GameRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(GameRestEndpoints.class);

    @Autowired
    private GameService gameService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Game createGame(
            @RequestParam String env,
            @RequestBody Game game,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Game gameResponse;
        try {
            request.setAttribute("requestBody", game);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            gameResponse = gameService.saveGame(game);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create game ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", gameResponse);
        return gameResponse;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Game> readGames(
            @RequestParam String env,
            @RequestParam(required = false) Long gameId,
            @RequestParam(required = false) String gameName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Game> gameList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (gameId != null) {
                Optional<Game> game = gameService.getGame(
                        gameId);
                if(game.isEmpty()) {
                    throw new NotFoundException("Game not found");
                } else {
                    gameList = new ArrayList<>();
                    gameList.add(game.get());
                }
            } else if (gameName != null) {
                Optional<Game> game = gameService.getGame(
                        gameName);
                if(game.isEmpty()) {
                    throw new NotFoundException("Game not found");
                } else {
                    gameList = new ArrayList<>();
                    gameList.add(game.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented game ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read game ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", gameList);
        return gameList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Game> updateGames(
            @RequestParam String env,
            @RequestBody Game game,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Game> gameList;
        try {
            request.setAttribute("requestBody", game);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            game = gameService.updateGame(
                    game);
            gameList = new ArrayList<>();
            gameList.add(game);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update game ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", gameList);
        return gameList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGames(@RequestParam String env,
                               @RequestParam(required = false) Long gameId,
                               @RequestParam(required = false) String gameName,
                               @RequestAttribute BigInteger currentRequestNumber,
                               ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(gameId != null) {
                gameService.deleteGame(gameId);
            } else if (gameName != null) {
                gameService.deleteGameByGameName(
                        gameName);
            } else {
                throw new NotImplementedException("This delete query is not implemented game ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete game ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
