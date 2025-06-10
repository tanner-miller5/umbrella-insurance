package com.umbrella.insurance.endpoints.rest.records.playerRecords.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.PlayerRecord;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.records.playerRecords.v1.db.PlayerRecordPrivilege;
import com.umbrella.insurance.core.models.records.playerRecords.v1.db.jpa.PlayerRecordService;
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
@RequestMapping(PlayerRecordPrivilege.PATH)
public class PlayerRecordRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(PlayerRecordRestEndpoints.class);

    @Autowired
    private PlayerRecordService playerRecordService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PlayerRecord createPlayerRecord(
            @RequestParam String env,
            @RequestBody PlayerRecord playerRecord,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        PlayerRecord playerRecordRecordResponse;
        try {
            request.setAttribute("requestBody", playerRecord);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            playerRecordRecordResponse = playerRecordService.savePlayerRecord(playerRecord);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create playerRecord record");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", playerRecordRecordResponse);
        return playerRecordRecordResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PlayerRecord> readPlayerRecords(
            @RequestParam String env,
            @RequestParam(required = false) Long playerRecordId,
            @RequestParam(required = false) Long seasonId,
            @RequestParam(required = false) Long playerId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PlayerRecord> playerRecordList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (playerRecordId != null) {
                Optional<PlayerRecord> playerRecord = playerRecordService.getPlayerRecord(
                        playerRecordId);
                if (playerRecord.isEmpty()) {
                    throw new NotFoundException("Unable to read playerRecord record");
                } else {
                    playerRecordList = new ArrayList<>();
                    playerRecordList.add(playerRecord.get());
                }
            } else if (seasonId != null && playerId != null) {
                Optional<PlayerRecord> playerRecord = playerRecordService
                        .getPlayerRecordBySeasonIdAndPlayerId(
                                seasonId, playerId);
                if (playerRecord.isEmpty()) {
                    throw new NotFoundException("Unable to read playerRecord record");
                } else {
                    playerRecordList = new ArrayList<>();
                    playerRecordList.add(playerRecord.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented playerRecord record");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read playerRecord record");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", playerRecordList);
        return playerRecordList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PlayerRecord> updatePlayerRecords(
            @RequestParam String env,
            @RequestBody PlayerRecord playerRecord,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PlayerRecord> playerRecordList;
        try {
            request.setAttribute("requestBody", playerRecord);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            playerRecord = playerRecordService.updatePlayerRecord(
                    playerRecord);
            playerRecordList = new ArrayList<>();
            playerRecordList.add(playerRecord);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update playerRecord record");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", playerRecordList);
        return playerRecordList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePlayerRecords(
            @RequestParam String env,
            @RequestParam(required = false) Long playerRecordId,
            @RequestParam(required = false) Long seasonId,
            @RequestParam(required = false) Long playerId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(playerRecordId != null) {
                playerRecordService.deletePlayerRecord(
                        playerRecordId);
            } else if (seasonId != null && playerId != null) {
                playerRecordService
                        .deletePlayerRecordBySeasonIdAndPlayerId(
                                seasonId, playerRecordId);
            } else {
                throw new NotImplementedException("This delete query is not implemented playerRecord record");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete playerRecord record");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
