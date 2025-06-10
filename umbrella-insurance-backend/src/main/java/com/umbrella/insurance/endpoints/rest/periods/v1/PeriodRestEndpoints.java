package com.umbrella.insurance.endpoints.rest.periods.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.periods.v1.db.PeriodPrivilege;
import com.umbrella.insurance.core.models.entities.*;

import com.umbrella.insurance.core.models.periods.v1.db.jpa.PeriodService;
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
@RequestMapping(PeriodPrivilege.PATH)
public class PeriodRestEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(PeriodRestEndpoints.class);

    @Autowired
    private PeriodService periodService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Period createPeriod(
            @RequestParam String env,
            @RequestBody Period period,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Period periodResponse;
        try {
            request.setAttribute("requestBody", period);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            periodResponse = periodService.savePeriod(period);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create period ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", periodResponse);
        return periodResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Period> readPeriods(
            @RequestParam String env,
            @RequestParam(required = false) Long periodId,
            @RequestParam(required = false) Long gameId,
            @RequestParam(required = false) Long periodNumber,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Period> periodList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (periodId != null) {
                Optional<Period> period = periodService.getPeriodById(
                        periodId);
                if (period.isEmpty()) {
                    throw new NotFoundException("Unable to read period ");
                } else {
                    periodList = new ArrayList<>();
                    periodList.add(period.get());
                }
            } else if (gameId != null && periodNumber != null) {
                Optional<Period> period = periodService
                        .getPeriodByGameIdAndPeriodNumber(gameId, periodNumber);
                if (period.isEmpty()) {
                    throw new NotFoundException("Unable to read period ");
                } else {
                    periodList = new ArrayList<>();
                    periodList.add(period.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented period ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read period ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", periodList);
        return periodList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Period> updatePeriods(
            @RequestParam String env,
            @RequestBody Period period,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Period> periodList;
        try {
            request.setAttribute("requestBody", period);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            period = periodService.updatePeriod(
                    period);
            periodList = new ArrayList<>();
            periodList.add(period);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update period ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", periodList);
        return periodList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePeriods(
            @RequestParam String env,
            @RequestParam(required = false) Long periodId,
            @RequestParam(required = false) Long gameId,
            @RequestParam(required = false) Long periodNumber,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(periodId != null) {
                periodService.deletePeriod(
                        periodId);
            } else if (gameId != null && periodNumber != null) {
                periodService
                        .deletePeriodByGameIdAndPeriodNumber(
                                gameId, periodNumber);
            } else {
                throw new NotImplementedException("This delete query is not implemented period ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete period ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
