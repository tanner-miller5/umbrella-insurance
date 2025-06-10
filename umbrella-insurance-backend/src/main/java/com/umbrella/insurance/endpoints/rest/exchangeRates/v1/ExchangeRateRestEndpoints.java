package com.umbrella.insurance.endpoints.rest.exchangeRates.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exchangeRates.v1.db.ExchangeRatePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.exchangeRates.v1.db.jpa.ExchangeRateService;
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
@RequestMapping(ExchangeRatePrivilege.PATH)
public class ExchangeRateRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateRestEndpoints.class);

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private UnitService unitService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ExchangeRate createExchangeRate(
            @RequestParam String env,
            @RequestBody ExchangeRate exchangeRate,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        ExchangeRate exchangeRateResponse;
        try {
            request.setAttribute("requestBody", exchangeRate);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            exchangeRateResponse = exchangeRateService.saveExchangeRate(exchangeRate);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create exchangeRate ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", exchangeRateResponse);
        return exchangeRateResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ExchangeRate> readExchangeRates(
            @RequestParam String env,
            @RequestParam(required = false) Long exchangeRateId,
            @RequestParam(required = false) Long unitId1,
            @RequestParam(required = false) Long unitId2,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<ExchangeRate> exchangeRateList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (exchangeRateId != null) {
                Optional<ExchangeRate> exchangeRate = exchangeRateService.getExchangeRateByExchangeRateId(
                        exchangeRateId);
                if(exchangeRate.isEmpty()) {
                    throw new NotFoundException("Exchange rate not found");
                } else {
                    exchangeRateList = new ArrayList<>();
                    exchangeRateList.add(exchangeRate.get());
                }
            } else if (unitId1 != null && unitId2 != null) {
                Unit unit1 = unitService.getUnitByUnitId(unitId1).get();
                Unit unit2 = unitService.getUnitByUnitId(unitId2).get();
                Optional<ExchangeRate> exchangeRate = exchangeRateService.getExchangeRateByUnit1AndUnit2(
                        unit1, unit2);
                if(exchangeRate.isEmpty()) {
                    throw new NotFoundException("Exchange rate not found");
                } else {
                    exchangeRateList = new ArrayList<>();
                    exchangeRateList.add(exchangeRate.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented exchangeRate ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read exchangeRate ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", exchangeRateList);
        return exchangeRateList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ExchangeRate> updateExchangeRates(
            @RequestParam String env,
            @RequestBody ExchangeRate exchangeRate,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<ExchangeRate> exchangeRateList;
        try {
            request.setAttribute("requestBody", exchangeRate);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            exchangeRate = exchangeRateService.updateExchangeRate(
                    exchangeRate);
            exchangeRateList = new ArrayList<>();
            exchangeRateList.add(exchangeRate);

        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update exchangeRate ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", exchangeRateList);
        return exchangeRateList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteExchangeRates(@RequestParam String env,
                                @RequestParam(required = false) Long exchangeRateId,
                                   @RequestParam(required = false) Long unitId1,
                                   @RequestParam(required = false) Long unitId2,
                                @RequestAttribute BigInteger currentRequestNumber,
                                ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(exchangeRateId != null) {
                exchangeRateService.deleteExchangeRate(exchangeRateId);
            } else if (unitId1 != null && unitId2 != null) {
                Unit unit1 = unitService.getUnitByUnitId(unitId1).get();
                Unit unit2 = unitService.getUnitByUnitId(unitId2).get();
                exchangeRateService.deleteExchangeRateByUnit1AndUnit2(
                        unit1, unit2);
            } else {
                throw new NotImplementedException("This delete query is not implemented exchangeRate ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete exchangeRate ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
