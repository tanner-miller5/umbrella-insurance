package com.umbrella.insurance.endpoints.rest.geographies.countries.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.geographies.countries.v1.db.CountryPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.geographies.countries.v1.db.jpa.CountryService;
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
@RequestMapping(CountryPrivilege.PATH)
public class CountryRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(CountryRestEndpoints.class);

    @Autowired
    private CountryService countryService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Country createCountry(
            @RequestParam String env,
            @RequestBody Country country,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Country countryResponse;
        try {
            request.setAttribute("requestBody", country);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            countryResponse = countryService.saveCountry(country);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create country ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", countryResponse);
        return countryResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Country> readCountries(
            @RequestParam String env,
            @RequestParam(required = false) Long countryId,
            @RequestParam(required = false) String countryName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Country> countryList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (countryId != null) {
                Optional<Country> country = countryService.findByCountryId(
                        countryId);
                if (country.isEmpty()) {
                    throw new NotFoundException("Unable to read country ");
                } else {
                    countryList = new ArrayList<>();
                    countryList.add(country.get());
                }
            } else if (countryName != null) {
                Optional<Country> country = countryService.findByCountryName(
                        countryName);
                if (country.isEmpty()) {
                    throw new NotFoundException("Unable to read country ");
                } else {
                    countryList = new ArrayList<>();
                    countryList.add(country.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented country ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read country ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", countryList);
        return countryList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Country> updateCountries(
            @RequestParam String env,
            @RequestBody Country country,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Country> countryList;
        try {
            request.setAttribute("requestBody", country);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            country = countryService.updateCountry(country);
            countryList = new ArrayList<>();
            countryList.add(country);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update country ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", countryList);
        return countryList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCountries(@RequestParam String env,
                           @RequestParam(required = false) Long countryId,
                           @RequestParam(required = false) String countryName,
                           @RequestAttribute BigInteger currentRequestNumber,
                           ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(countryId != null) {
                countryService.deleteCountry(countryId);
            } else if (countryName != null) {
                countryService.deleteCountryByCountryName(
                        countryName);
            } else {
                throw new NotImplementedException("This delete query is not implemented country ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete country ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
