package com.umbrella.insurance.endpoints.rest.units.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.units.v1.db.UnitPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
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
@RequestMapping(UnitPrivilege.PATH)
public class UnitRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(UnitRestEndpoints.class);

    @Autowired
    private UnitService unitService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Unit createUnit(
            @RequestParam String env,
            @RequestBody Unit unit,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Unit unitResponse;
        try {
            request.setAttribute("requestBody", unit);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            unitResponse = unitService.saveUnit(unit);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create unit ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", unitResponse);
        return unitResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Unit> readUnits(
            @RequestParam String env,
            @RequestParam(required = false) Long unitId,
            @RequestParam(required = false) String unitName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Unit> unitList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (unitId != null) {
                Optional<Unit> unit = unitService.getUnitByUnitId(
                        unitId);
                if (unit.isEmpty()) {
                    throw new NotFoundException("Unable to read unit ");
                } else {
                    unitList = new ArrayList<>();
                    unitList.add(unit.get());
                }
            } else if (unitName != null) {
                Optional<Unit> unit = unitService.getUnitByUnitName(
                        unitName);
                if (unit.isEmpty()) {
                    throw new NotFoundException("Unable to read unit ");
                } else {
                    unitList = new ArrayList<>();
                    unitList.add(unit.get());
                }
            } else {
                unitList = unitService.getUnits();
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read unit ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", unitList);
        return unitList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Unit> updateUnits(
            @RequestParam String env,
            @RequestBody Unit unit,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Unit> unitList;
        try {
            request.setAttribute("requestBody", unit);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            unit = unitService.updateUnit(unit);
            unitList = new ArrayList<>();
            unitList.add(unit);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update unit ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", unitList);
        return unitList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUnits(@RequestParam String env,
                                   @RequestParam(required = false) Long unitId,
                                   @RequestParam(required = false) String unitName,
                                   @RequestAttribute BigInteger currentRequestNumber,
                                   ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(unitId != null) {
                unitService.deleteUnit(unitId);
            } else if (unitName != null) {
                unitService.deleteUnitByUnitName(unitName);
            } else {
                throw new NotImplementedException("This delete query is not implemented unit ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete unit ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
