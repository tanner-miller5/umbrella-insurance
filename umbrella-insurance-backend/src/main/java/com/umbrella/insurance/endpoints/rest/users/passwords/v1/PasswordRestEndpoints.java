package com.umbrella.insurance.endpoints.rest.users.passwords.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.passwords.v1.db.PasswordPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.passwords.v1.db.jpa.PasswordService;
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
@RequestMapping(PasswordPrivilege.PATH)
public class PasswordRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(PasswordRestEndpoints.class);

    @Autowired
    private PasswordService passwordService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Password createPassword(
            @RequestParam String env,
            @RequestBody Password password,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Password passwordResponse;
        try {
            request.setAttribute("requestBody", password);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            passwordResponse = passwordService.savePassword(password);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create password");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", passwordResponse);
        return passwordResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Password> readPasswords(
            @RequestParam String env,
            @RequestParam(required = false) Long passwordId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Password> passwordList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (passwordId != null) {
                Optional<Password> password = passwordService.getPasswordByPasswordId(passwordId);
                if (password.isEmpty()) {
                    throw new NotFoundException("Unable to read password");
                } else {
                    passwordList = new ArrayList<>();
                    passwordList.add(password.get());
                }
            } else if (userId != null) {
                Optional<Password> password = passwordService
                        .getPasswordByUserId(userId);
                if (password.isEmpty()) {
                    throw new NotFoundException("Unable to read password");
                } else {
                    passwordList = new ArrayList<>();
                    passwordList.add(password.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented password");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read password ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", passwordList);
        return passwordList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Password> updatePasswords(
            @RequestParam String env,
            @RequestBody Password password,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Password> passwordList;
        try {
            request.setAttribute("requestBody", password);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            password = passwordService.updatePassword(password);
            passwordList = new ArrayList<>();
            passwordList.add(password);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update password");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", passwordList);
        return passwordList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePasswords(
            @RequestParam String env,
            @RequestParam(required = false) Long passwordId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(passwordId != null) {
                passwordService.deletePassword(passwordId);
            } else if (userId != null) {
                passwordService.deletePasswordByUserId(userId);
            } else {
                throw new NotImplementedException("This delete query is not implemented password");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete password ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
