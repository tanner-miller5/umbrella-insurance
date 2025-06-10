package com.umbrella.insurance.endpoints.rest.charities.v1;

import com.umbrella.insurance.core.models.charities.v1.db.CharityPrivilege;
import com.umbrella.insurance.core.models.charities.v1.db.jpa.CharityService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Charity;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
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
@RequestMapping(CharityPrivilege.PATH)
public class CharityRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(CharityRestEndpoints.class);

    @Autowired
    private CharityService charityService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Charity createCharity(
            @RequestParam String env,
            @RequestBody Charity charity,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Charity charityResponse;
        try {
            request.setAttribute("requestBody", charity);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            charityResponse = charityService.saveCharity(charity);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create charity ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", charityResponse);
        return charityResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Charity> readCharities(
            @RequestParam String env,
            @RequestParam(required = false) Long charityId,
            @RequestParam(required = false) String charityName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Charity> charityList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (charityId != null) {
                Optional<Charity> charity = charityService.getCharityByCharityId(charityId);
                if (charity.isEmpty()) {
                    throw new NotFoundException("Unable to read charity");
                } else {
                    charityList = new ArrayList<>();
                    charityList.add(charity.get());
                }
            } else if (charityName != null) {
                Optional<Charity> charity = charityService.getCharityByCharityName(
                        charityName);
                if (charity.isEmpty()) {
                    throw new NotFoundException("Unable to read charity");
                } else {
                    charityList = new ArrayList<>();
                    charityList.add(charity.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented charity ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read charity ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", charityList);
        return charityList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Charity> updateCharities(
            @RequestParam String env,
            @RequestBody Charity charity,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Charity> charityList;
        try {
            request.setAttribute("requestBody", charity);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            charity = charityService.updateCharity(
                    charity);
            charityList = new ArrayList<>();
            charityList.add(charity);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update charity ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", charityList);
        return charityList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCharities(@RequestParam String env,
                                   @RequestParam(required = false) Long charityId,
                                   @RequestParam(required = false) String charityName,
                                   @RequestAttribute BigInteger currentRequestNumber,
                                   ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(charityId != null) {
                charityService.deleteCharity(charityId);
            } else if (charityName != null) {
                charityService.deleteCharityByCharityName(charityName);
            } else {
                throw new NotImplementedException("This delete query is not implemented charity ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete charity ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
