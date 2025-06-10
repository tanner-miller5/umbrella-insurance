package com.umbrella.insurance.endpoints.rest.weeks.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.weeks.v1.db.WeekPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.weeks.v1.db.jpa.WeekService;
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
@RequestMapping(WeekPrivilege.PATH)
public class WeekRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(WeekRestEndpoints.class);

    @Autowired
    private WeekService weekService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Week createWeek(
            @RequestParam String env,
            @RequestBody Week week,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Week weekResponse;
        try {
            request.setAttribute("requestBody", week);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            weekResponse = weekService.saveWeek(week);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create week ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", weekResponse);
        return weekResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Week> readWeeks(
            @RequestParam String env,
            @RequestParam(required = false) Long weekId,
            @RequestParam(required = false) String weekTitle,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Week> weekList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (weekId != null) {
                Optional<Week> week = weekService.getWeekByWeekId(
                        weekId);
                if (week.isEmpty()) {
                    throw new NotFoundException("Unable to read week");
                } else {
                    weekList = new ArrayList<>();
                    weekList.add(week.get());
                }
            } else if (weekTitle != null) {
                Optional<Week> week = weekService
                        .getWeekByWeekTitle(weekTitle);
                if (week.isEmpty()) {
                    throw new NotFoundException("Unable to read week");
                } else {
                    weekList = new ArrayList<>();
                    weekList.add(week.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented week");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read week");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", weekList);
        return weekList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Week> updateWeeks(
            @RequestParam String env,
            @RequestBody Week week,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Week> weekList;
        try {
            request.setAttribute("requestBody", week);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            week = weekService.updateWeek(week);
            weekList = new ArrayList<>();
            weekList.add(week);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update week");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", weekList);
        return weekList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteWeeks(
            @RequestParam String env,
            @RequestParam(required = false) Long weekId,
            @RequestParam(required = false) String weekTitle,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(weekId != null) {
                weekService.deleteWeek(weekId);
            } else if (weekTitle != null) {
                weekService.deleteWeekByWeekTitle(
                                weekTitle);
            } else {
                throw new NotImplementedException("This delete query is not implemented week");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete week");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
