package com.umbrella.insurance.endpoints.rest.users.phoneNumbersHistory.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.phoneNumbersHistory.v1.db.PhoneNumberHistoryPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.phoneNumbersHistory.v1.db.jpa.PhoneNumberHistoryService;
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
@RequestMapping(PhoneNumberHistoryPrivilege.PATH)
public class PhoneNumberHistoryRestEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(PhoneNumberHistoryRestEndpoints.class);

    @Autowired
    private PhoneNumberHistoryService phoneNumberHistoryService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PhoneNumberHistory createPhoneNumberHistory(
            @RequestParam String env,
            @RequestBody PhoneNumberHistory phoneNumberHistory,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        PhoneNumberHistory phoneNumberHistoryResponse;
        try {
            request.setAttribute("requestBody", phoneNumberHistory);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            phoneNumberHistoryResponse = phoneNumberHistoryService.savePhoneNumberHistory(phoneNumberHistory);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create phoneNumberHistory");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", phoneNumberHistoryResponse);
        return phoneNumberHistoryResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PhoneNumberHistory> readPhoneNumberHistory(
            @RequestParam String env,
            @RequestParam(required = false) Long phoneNumberHistoryId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PhoneNumberHistory> phoneNumberHistoryList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (phoneNumberHistoryId != null) {
                Optional<PhoneNumberHistory> phoneNumberHistory = phoneNumberHistoryService.getPhoneNumberHistoryByPhoneNumberHistoryId(
                        phoneNumberHistoryId);
                if (phoneNumberHistory.isEmpty()) {
                    throw new NotFoundException("Unable to read phoneNumberHistory");
                } else {
                    phoneNumberHistoryList = new ArrayList<>();
                    phoneNumberHistoryList.add(phoneNumberHistory.get());
                }
            } else if (userId != null) {
                Optional<PhoneNumberHistory> phoneNumberHistory = phoneNumberHistoryService
                        .getPhoneNumberHistoryByUserId(
                                userId);
                if (phoneNumberHistory.isEmpty()) {
                    throw new NotFoundException("Unable to read phoneNumberHistory");
                } else {
                    phoneNumberHistoryList = new ArrayList<>();
                    phoneNumberHistoryList.add(phoneNumberHistory.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented phoneNumberHistory");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read phoneNumberHistory");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", phoneNumberHistoryList);
        return phoneNumberHistoryList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PhoneNumberHistory> updatePhoneNumberHistory(
            @RequestParam String env,
            @RequestBody PhoneNumberHistory phoneNumberHistory,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PhoneNumberHistory> phoneNumberHistoryList;
        try {
            request.setAttribute("requestBody", phoneNumberHistory);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            phoneNumberHistory = phoneNumberHistoryService.updatePhoneNumberHistory(
                    phoneNumberHistory);
            phoneNumberHistoryList = new ArrayList<>();
            phoneNumberHistoryList.add(phoneNumberHistory);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update phoneNumberHistory");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", phoneNumberHistoryList);
        return phoneNumberHistoryList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePhoneNumberHistory(
            @RequestParam String env,
            @RequestParam(required = false) Long phoneNumberHistoryId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(phoneNumberHistoryId != null) {
                phoneNumberHistoryService.deletePhoneNumberHistory(
                        phoneNumberHistoryId);
            } else if (userId != null) {
                phoneNumberHistoryService
                        .deletePhoneNumberHistoryByUserId(userId);
            } else {
                throw new NotImplementedException("This delete query is not implemented phoneNumberHistory");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete phoneNumberHistory");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
