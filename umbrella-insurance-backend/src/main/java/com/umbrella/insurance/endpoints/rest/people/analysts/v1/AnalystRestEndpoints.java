package com.umbrella.insurance.endpoints.rest.people.analysts.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.people.analysts.v1.db.AnalystPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.people.analysts.v1.db.jpa.AnalystService;
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
@RequestMapping(AnalystPrivilege.PATH)
public class AnalystRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(AnalystRestEndpoints.class);

    @Autowired
    private AnalystService analystService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Analyst createAnalyst(
            @RequestParam String env,
            @RequestBody Analyst analyst,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Analyst analystResponse;
        try {
            request.setAttribute("requestBody", analyst);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            analystResponse = analystService.saveAnalyst(analyst);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create analyst ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", analystResponse);
        return analystResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Analyst> readAnalysts(
            @RequestParam String env,
            @RequestParam(required = false) Long analystId,
            @RequestParam(required = false) Long personId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Analyst> analystList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (analystId != null) {
                Optional<Analyst> analyst = analystService.getAnalystById(
                        analystId);
                if (analyst.isEmpty()) {
                    throw new NotFoundException("Unable to read analyst ");
                } else {
                    analystList = new ArrayList<>();
                    analystList.add(analyst.get());
                }
            } else if (personId != null) {
                Optional<Analyst> analyst = analystService
                        .getAnalystByPersonId(personId);
                if (analyst.isEmpty()) {
                    throw new NotFoundException("Unable to read analyst ");
                } else {
                    analystList = new ArrayList<>();
                    analystList.add(analyst.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented analyst ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read analyst ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", analystList);
        return analystList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Analyst> updateAnalysts(
            @RequestParam String env,
            @RequestBody Analyst analyst,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Analyst> analystList;
        try {
            request.setAttribute("requestBody", analyst);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            analyst = analystService.updateAnalyst(analyst);
            analystList = new ArrayList<>();
            analystList.add(analyst);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update analyst ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", analystList);
        return analystList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAnalysts(
            @RequestParam String env,
            @RequestParam(required = false) Long analystId,
            @RequestParam(required = false) Long personId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(analystId != null) {
                analystService.deleteAnalyst(
                        analystId);
            } else if (personId != null) {
                analystService
                        .deleteAnalystByPersonId(
                                personId);
            } else {
                throw new NotImplementedException("This delete query is not implemented analyst ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete analyst ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
