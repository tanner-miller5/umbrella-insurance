package com.umbrella.insurance.endpoints.rest.schedules.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.schedules.v1.db.SchedulePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.schedules.v1.db.jpa.ScheduleService;
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
@RequestMapping(SchedulePrivilege.PATH)
public class ScheduleRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleRestEndpoints.class);

    @Autowired
    private ScheduleService scheduleService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Schedule createSchedule(
            @RequestParam String env,
            @RequestBody Schedule schedule,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Schedule scheduleResponse;
        try {
            request.setAttribute("requestBody", schedule);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            scheduleResponse = scheduleService.saveSchedule(schedule);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create schedule ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", scheduleResponse);
        return scheduleResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Schedule> readSchedules(
            @RequestParam String env,
            @RequestParam(required = false) Long scheduleId,
            @RequestParam(required = false) Long gameId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Schedule> scheduleList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (scheduleId != null) {
                Optional<Schedule> schedule = scheduleService.getScheduleById(
                        scheduleId);
                if (schedule.isEmpty()) {
                    throw new NotFoundException("Unable to read schedule ");
                } else {
                    scheduleList = new ArrayList<>();
                    scheduleList.add(schedule.get());
                }
            } else if (gameId != null) {
                Optional<Schedule> schedule = scheduleService
                        .getScheduleByGameId(gameId);
                if (schedule.isEmpty()) {
                    throw new NotFoundException("Unable to read schedule ");
                } else {
                    scheduleList = new ArrayList<>();
                    scheduleList.add(schedule.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented schedule ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read schedule ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", scheduleList);
        return scheduleList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Schedule> updateSchedules(
            @RequestParam String env,
            @RequestBody Schedule schedule,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Schedule> scheduleList;
        try {
            request.setAttribute("requestBody", schedule);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            schedule = scheduleService.updateSchedule(
                    schedule);
            scheduleList = new ArrayList<>();
            scheduleList.add(schedule);

        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update schedule ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", scheduleList);
        return scheduleList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSchedules(
            @RequestParam String env,
            @RequestParam(required = false) Long scheduleId,
            @RequestParam(required = false) Long gameId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(scheduleId != null) {
                scheduleService.deleteSchedule(
                        scheduleId);
            } else if (gameId != null) {
                scheduleService
                        .deleteScheduleByGameId(
                                gameId);
            } else {
                throw new NotImplementedException("This delete query is not implemented schedule ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete schedule ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
