package com.umbrella.insurance.endpoints.rest.geographies.states.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.geographies.states.v1.db.StatePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.geographies.states.v1.db.jpa.StateService;
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
@RequestMapping(StatePrivilege.PATH)
public class StateRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(StateRestEndpoints.class);

    @Autowired
    private StateService stateService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    State createState(
            @RequestParam String env,
            @RequestBody State state,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        State stateResponse;
        try {
            request.setAttribute("requestBody", state);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            stateResponse = stateService.saveState(state);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create state ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", stateResponse);
        return stateResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<State> readStates(
            @RequestParam String env,
            @RequestParam(required = false) Long stateId,
            @RequestParam(required = false) String stateName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<State> stateList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (stateId != null) {
                Optional<State> state = stateService.getStateById(
                        stateId);
                if (state.isEmpty()) {
                    throw new NotFoundException("Unable to read state ");
                } else {
                    stateList = new ArrayList<>();
                    stateList.add(state.get());
                }
            } else if (stateName != null) {
                Optional<State> state = stateService.getStateByStateName(
                        stateName);
                if (state.isEmpty()) {
                    throw new NotFoundException("Unable to read state ");
                } else {
                    stateList = new ArrayList<>();
                    stateList.add(state.get());
                }
            } else {
                stateList = stateService.getStatesOrderByStateName();
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read state ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", stateList);
        return stateList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<State> updateStates(
            @RequestParam String env,
            @RequestBody State state,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<State> stateList;
        try {
            request.setAttribute("requestBody", state);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            state = stateService.updateState(state);
            stateList = new ArrayList<>();
            stateList.add(state);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update state ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", stateList);
        return stateList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteStates(@RequestParam String env,
                           @RequestParam(required = false) Long stateId,
                           @RequestParam(required = false) String stateName,
                           @RequestAttribute BigInteger currentRequestNumber,
                           ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(stateId != null) {
                stateService.deleteState(stateId);
            } else if (stateName != null) {
                stateService.deleteStateByStateName(
                        stateName);
            } else {
                throw new NotImplementedException("This delete query is not implemented state ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete state ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
