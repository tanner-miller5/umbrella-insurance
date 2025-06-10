package com.umbrella.insurance.endpoints.rest.periods.periodTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.periods.periodTypes.v1.db.PeriodTypePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.periods.periodTypes.v1.db.jpa.PeriodTypeService;
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
@RequestMapping(PeriodTypePrivilege.PATH)
public class PeriodTypeRestEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(PeriodTypeRestEndpoints.class);

    @Autowired
    private PeriodTypeService periodTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PeriodType createPeriodType(
            @RequestParam String env,
            @RequestBody PeriodType periodType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        PeriodType periodTypeResponse;
        try {
            request.setAttribute("requestBody", periodType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            periodTypeResponse = periodTypeService.savePeriodType(periodType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create periodType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", periodTypeResponse);
        return periodTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PeriodType> readPeriodTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long periodTypeId,
            @RequestParam(required = false) String periodTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PeriodType> periodTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (periodTypeId != null) {
                Optional<PeriodType> periodType = periodTypeService.getPeriodTypeById(
                        periodTypeId);
                if (periodType.isEmpty()) {
                    throw new NotFoundException("Unable to read periodType ");
                } else {
                    periodTypeList = new ArrayList<>();
                    periodTypeList.add(periodType.get());
                }
            } else if (periodTypeName != null) {
                Optional<PeriodType> periodType = periodTypeService
                        .getPeriodTypeByPeriodTypeName(periodTypeName);
                if (periodType.isEmpty()) {
                    throw new NotFoundException("Unable to read periodType ");
                } else {
                    periodTypeList = new ArrayList<>();
                    periodTypeList.add(periodType.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented periodType ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read periodType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", periodTypeList);
        return periodTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PeriodType> updatePeriodTypes(
            @RequestParam String env,
            @RequestBody PeriodType periodType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PeriodType> periodTypeList;
        try {
            request.setAttribute("requestBody", periodType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            periodType = periodTypeService.updatePeriodType(
                    periodType);
            periodTypeList = new ArrayList<>();
            periodTypeList.add(periodType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update periodType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", periodTypeList);
        return periodTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePeriodTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long periodTypeId,
            @RequestParam(required = false) String periodTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(periodTypeId != null) {
                periodTypeService.deletePeriodType(
                        periodTypeId);
            } else if (periodTypeName != null) {
                periodTypeService
                        .deletePeriodTypeByPeriodTypeName(
                                periodTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented periodType ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete periodType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
