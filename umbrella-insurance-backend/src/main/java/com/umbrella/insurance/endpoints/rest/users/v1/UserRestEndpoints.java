package com.umbrella.insurance.endpoints.rest.users.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.v4.db.UserPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import jakarta.servlet.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(UserPrivilege.PATH)
public class UserRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(UserRestEndpoints.class);

    @Autowired
    private UserService userService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    User createUser(
            @RequestParam String env,
            @RequestBody User user,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        User userResponse;
        try {
            request.setAttribute("requestBody", user);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            userResponse = userService.saveUser(user);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create user");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", userResponse);
        return userResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<User> readUsers(
            @RequestParam String env,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long personId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<User> userList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (userId != null) {
                Optional<User> user = userService.getUserByUserId(userId);
                if (user.isEmpty()) {
                    throw new NotFoundException("Unable to read user");
                } else {
                    userList = new ArrayList<>();
                    userList.add(user.get());
                }
            } else if (personId != null) {
                Optional<User> user = userService.getUserByPersonId(personId);
                if (user.isEmpty()) {
                    throw new NotFoundException("Unable to read user");
                } else {
                    userList = new ArrayList<>();
                    userList.add(user.get());
                }
            } else {
                throw new NotImplementedException("This update query is not implemented user");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read user");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", userList);
        return userList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<User> updateUsers(
            @RequestParam String env,
            @RequestBody User user,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<User> userList;
        try {
            request.setAttribute("requestBody", user);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            user = userService.updateUser(user);
            userList = new ArrayList<>();
            userList.add(user);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update user");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", userList);
        return userList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUsers(@RequestParam String env,
                                   @RequestParam(required = false) Long userId,
                                   @RequestParam(required = false) Long personId,
                                   @RequestAttribute BigInteger currentRequestNumber,
                                   ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(userId != null) {
                userService.deleteUser(userId);
            } else if (personId != null) {
                userService.deleteUserByPersonId(personId);
            } else {
                throw new NotImplementedException("This delete query is not implemented user");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete user");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
