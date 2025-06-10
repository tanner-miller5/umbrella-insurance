package com.umbrella.insurance.endpoints.rest.geographies.cities.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.geographies.cities.v1.db.CityPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.geographies.cities.v1.db.jpa.CityService;
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
@RequestMapping(CityPrivilege.PATH)
public class CityRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(CityRestEndpoints.class);

    @Autowired
    private CityService cityService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    City createCity(
            @RequestParam String env,
            @RequestBody City city,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        City cityResponse;
        try {
            request.setAttribute("requestBody", city);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            cityResponse = cityService.saveCity(city);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create city ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cityResponse);
        return cityResponse;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<City> readCities(
            @RequestParam String env,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) String cityName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<City> cityList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (cityId != null) {
                Optional<City> city = cityService.getCityById(cityId);
                if (city.isEmpty()) {
                    throw new NotFoundException("Unable to read city ");
                } else {
                    cityList = new ArrayList<>();
                    cityList.add(city.get());
                }
            } else if (cityName != null) {
                Optional<City> city = cityService.getCityByCityName(
                        cityName);
                if (city.isEmpty()) {
                    throw new NotFoundException("Unable to read city ");
                } else {
                    cityList = new ArrayList<>();
                    cityList.add(city.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented city ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read city ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cityList);
        return cityList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<City> updateCities(
            @RequestParam String env,
            @RequestBody City city,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<City> cityList;
        try {
            request.setAttribute("requestBody", city);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            city = cityService.updateCity(city);
            cityList = new ArrayList<>();
            cityList.add(city);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update city ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cityList);
        return cityList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCities(@RequestParam String env,
                               @RequestParam(required = false) Long cityId,
                               @RequestParam(required = false) String cityName,
                               @RequestAttribute BigInteger currentRequestNumber,
                               ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(cityId != null) {
                cityService.deleteCity(cityId);
            } else if (cityName != null) {
                cityService.deleteCityByCityName(cityName);
            } else {
                throw new NotImplementedException("This delete query is not implemented city ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete city ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
