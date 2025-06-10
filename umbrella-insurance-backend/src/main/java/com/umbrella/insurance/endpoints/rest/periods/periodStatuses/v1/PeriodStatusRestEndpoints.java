package com.umbrella.insurance.endpoints.rest.periods.periodStatuses.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.periods.periodStatuses.v1.db.PeriodStatusPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.periods.periodStatuses.v1.db.jpa.PeriodStatusService;
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
@RequestMapping(PeriodStatusPrivilege.PATH)
public class PeriodStatusRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(PeriodStatusRestEndpoints.class);

    @Autowired
    private PeriodStatusService periodStatusService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PeriodStatus createPeriodStatus(
            @RequestParam String env,
            @RequestBody PeriodStatus periodStatus,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        PeriodStatus periodStatusResponse;
        try {
            request.setAttribute("requestBody", periodStatus);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            periodStatusResponse = periodStatusService.savePeriodStatus(periodStatus);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create periodStatus ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", periodStatusResponse);
        return periodStatusResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PeriodStatus> readPeriodStatuses(
            @RequestParam String env,
            @RequestParam(required = false) Long periodStatusId,
            @RequestParam(required = false) String periodStatusName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PeriodStatus> periodStatusList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (periodStatusId != null) {
                Optional<PeriodStatus> periodStatus = periodStatusService.getPeriodStatus(
                        periodStatusId);
                if (periodStatus.isEmpty()) {
                    throw new NotFoundException("Unable to read periodStatus ");
                } else {
                    periodStatusList = new ArrayList<>();
                    periodStatusList.add(periodStatus.get());
                }
            } else if (periodStatusName != null) {
                Optional<PeriodStatus> periodStatus = periodStatusService.getPeriodStatus(periodStatusName);
                if (periodStatus.isEmpty()) {
                    throw new NotFoundException("Unable to read periodStatus ");
                } else {
                    periodStatusList = new ArrayList<>();
                    periodStatusList.add(periodStatus.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented periodStatus ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read periodStatus ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", periodStatusList);
        return periodStatusList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PeriodStatus> updatePeriodStatuses(
            @RequestParam String env,
            @RequestBody PeriodStatus periodStatus,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PeriodStatus> periodStatusList;
        try {
            request.setAttribute("requestBody", periodStatus);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            periodStatus = periodStatusService.updatePeriodStatus(
                    periodStatus);
            periodStatusList = new ArrayList<>();
            periodStatusList.add(periodStatus);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update periodStatus ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", periodStatusList);
        return periodStatusList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePeriodStatuses(
            @RequestParam String env,
            @RequestParam(required = false) Long periodStatusId,
            @RequestParam(required = false) String periodStatusName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(periodStatusId != null) {
                periodStatusService.deletePeriodStatus(periodStatusId);
            } else if (periodStatusName != null) {
                periodStatusService.deletePeriodStatusByPeriodStatusName(
                                periodStatusName);
            } else {
                throw new NotImplementedException("This delete query is not implemented periodStatus ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete periodStatus ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
