package com.umbrella.insurance.endpoints.rest.dates.holidays.v1;

import com.umbrella.insurance.core.models.dates.holidays.v1.db.HolidayPrivilege;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.dates.holidays.v1.db.jpa.HolidayService;
import com.umbrella.insurance.core.models.entities.Holiday;
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
@RequestMapping(HolidayPrivilege.PATH)
public class HolidayRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(HolidayRestEndpoints.class);

    @Autowired
    private HolidayService holidayService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Holiday createHoliday(
            @RequestParam String env,
            @RequestBody Holiday holiday,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Holiday holidayResponse;
        try {
            request.setAttribute("requestBody", holiday);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            holidayResponse = holidayService.saveHoliday(holiday);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create holiday ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", holidayResponse);
        return holidayResponse;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Holiday> readHolidays(
            @RequestParam String env,
            @RequestParam(required = false) Long holidayId,
            @RequestParam(required = false) String holidayName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Holiday> holidayList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (holidayId != null) {
                Optional<Holiday> holiday = holidayService.getHolidayById(holidayId);
                if(holiday.isEmpty()) {
                    throw new NotFoundException("Unable to find holiday");
                } else {
                    holidayList = new ArrayList<>();
                    holidayList.add(holiday.get());
                }
            } else if (holidayName != null) {
                Optional<Holiday> holiday = holidayService.getHolidayByHolidayName(
                        holidayName);
                if(holiday.isEmpty()) {
                    throw new NotFoundException("Unable to find holiday");
                } else {
                    holidayList = new ArrayList<>();
                    holidayList.add(holiday.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented holiday ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read holiday ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", holidayList);
        return holidayList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Holiday> updateHolidays(
            @RequestParam String env,
            @RequestBody Holiday holiday,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Holiday> holidayList;
        try {
            request.setAttribute("requestBody", holiday);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            holiday = holidayService.updateHoliday(
                    holiday);
            holidayList = new ArrayList<>();
            holidayList.add(holiday);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update holiday ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", holidayList);
        return holidayList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteHolidays(@RequestParam String env,
                       @RequestParam(required = false) Long holidayId,
                       @RequestParam(required = false) String holidayName,
                       @RequestAttribute BigInteger currentRequestNumber,
                                   ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(holidayId != null) {
                holidayService.deleteHoliday(holidayId);
            } else if (holidayName != null) {
                holidayService.deleteHolidayByHolidayName(holidayName);
            } else {
                throw new NotImplementedException("This delete query is not implemented holiday ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete holiday ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
