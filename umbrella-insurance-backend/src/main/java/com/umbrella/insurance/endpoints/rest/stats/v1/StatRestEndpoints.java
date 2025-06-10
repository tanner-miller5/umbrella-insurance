package com.umbrella.insurance.endpoints.rest.stats.v1;

import com.umbrella.insurance.core.models.entities.Stat;
import com.umbrella.insurance.core.models.stats.v1.db.StatPrivilege;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.stats.v1.db.jpa.StatService;
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
@RequestMapping(StatPrivilege.PATH)
public class StatRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(StatRestEndpoints.class);

    @Autowired
    private StatService statService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Stat createStat(
            @RequestParam String env,
            @RequestBody Stat stat,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Stat statResponse;
        try {
            request.setAttribute("requestBody", stat);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            statResponse = statService.saveStat(stat);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create stat ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", statResponse);
        return statResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Stat> readStats(
            @RequestParam String env,
            @RequestParam(required = false) Long statId,
            @RequestParam(required = false) String statName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Stat> statList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (statId != null) {
                Optional<Stat> stat = statService.getStatById(statId);
                if (stat.isEmpty()) {
                    throw new NotFoundException("Unable to read stat ");
                } else {
                    statList = new ArrayList<>();
                    statList.add(stat.get());
                }
            } else if (statName != null) {
                Optional<Stat> stat = statService.getStatByStatName(
                        statName);
                if (stat.isEmpty()) {
                    throw new NotFoundException("Unable to read stat ");
                } else {
                    statList = new ArrayList<>();
                    statList.add(stat.get());
                }
            } else {
                statList = statService.getStats();
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read stat ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", statList);
        return statList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Stat> updateStats(
            @RequestParam String env,
            @RequestBody Stat stat,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Stat> statList;
        try {
            request.setAttribute("requestBody", stat);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            stat = statService.updateStat(stat);
            statList = new ArrayList<>();
            statList.add(stat);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update stat ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", statList);
        return statList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteStats(@RequestParam String env,
                                   @RequestParam(required = false) Long statId,
                                   @RequestParam(required = false) String statName,
                                   @RequestAttribute BigInteger currentRequestNumber,
                                   ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(statId != null) {
                statService.deleteStat(statId);
            } else if (statName != null) {
                statService.deleteStatByStatName(statName);
            } else {
                throw new NotImplementedException("This delete query is not implemented stat ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete stat ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
