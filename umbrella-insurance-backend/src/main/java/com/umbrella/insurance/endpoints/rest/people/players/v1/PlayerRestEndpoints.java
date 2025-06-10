package com.umbrella.insurance.endpoints.rest.people.players.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.people.players.v1.db.PlayerPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.people.players.v1.db.jpa.PlayerService;
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
@RequestMapping(PlayerPrivilege.PATH)
public class PlayerRestEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(PlayerRestEndpoints.class);

    @Autowired
    private PlayerService playerService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Player createPlayer(
            @RequestParam String env,
            @RequestBody Player player,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Player playerResponse;
        try {
            request.setAttribute("requestBody", player);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            playerResponse = playerService.savePlayer(player);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create player ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", playerResponse);
        return playerResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Player> readPlayers(
            @RequestParam String env,
            @RequestParam(required = false) Long playerId,
            @RequestParam(required = false) Long personId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Player> playerList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (playerId != null) {
                Optional<Player> player = playerService.getPlayerById(
                        playerId);
                if (player.isEmpty()) {
                    throw new NotFoundException("Unable to read player ");
                } else {
                    playerList = new ArrayList<>();
                    playerList.add(player.get());
                }
            } else if (personId != null) {
                Optional<Player> player = playerService
                        .getPlayerByPersonId(personId);
                if (player.isEmpty()) {
                    throw new NotFoundException("Unable to read player ");
                } else {
                    playerList = new ArrayList<>();
                    playerList.add(player.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented player ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read player ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", playerList);
        return playerList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Player> updatePlayers(
            @RequestParam String env,
            @RequestBody Player player,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Player> playerList;
        try {
            request.setAttribute("requestBody", player);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            player = playerService.updatePlayer(
                    player);
            playerList = new ArrayList<>();
            playerList.add(player);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update player");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", playerList);
        return playerList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePlayers(
            @RequestParam String env,
            @RequestParam(required = false) Long playerId,
            @RequestParam(required = false) Long personId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(playerId != null) {
                playerService.deletePlayer(playerId);
            } else if (personId != null) {
                playerService.deleteByPersonId(personId);

            } else {
                throw new NotImplementedException("This delete query is not implemented player ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete player ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
