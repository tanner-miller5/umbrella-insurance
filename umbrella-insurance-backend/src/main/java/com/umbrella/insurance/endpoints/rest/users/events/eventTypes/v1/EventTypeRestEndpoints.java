package com.umbrella.insurance.endpoints.rest.users.events.eventTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.events.eventTypes.v1.db.EventTypePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.events.eventTypes.v1.db.jpa.EventTypeService;
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
@RequestMapping(EventTypePrivilege.PATH)
public class EventTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(EventTypeRestEndpoints.class);

    @Autowired
    private EventTypeService eventTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    EventType createEventType(
            @RequestParam String env,
            @RequestBody EventType eventType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        EventType eventTypeResponse;
        try {
            request.setAttribute("requestBody", eventType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            eventTypeResponse = eventTypeService.saveEventType(eventType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create eventType");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", eventTypeResponse);
        return eventTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<EventType> readEventTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long eventTypeId,
            @RequestParam(required = false) String eventTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<EventType> eventTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (eventTypeId != null) {
                Optional<EventType> eventType = eventTypeService.getEventTypeById(
                        eventTypeId);
                if (eventType.isEmpty()) {
                    throw new NotFoundException("Unable to read eventType ");
                } else {
                    eventTypeList = new ArrayList<>();
                    eventTypeList.add(eventType.get());
                }
            } else if (eventTypeName != null) {
                Optional<EventType> eventType = eventTypeService
                        .getEventTypeByEventTypeName(eventTypeName);
                if (eventType.isEmpty()) {
                    throw new NotFoundException("Unable to read eventType");
                } else {
                    eventTypeList = new ArrayList<>();
                    eventTypeList.add(eventType.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented eventType");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read eventType");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", eventTypeList);
        return eventTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<EventType> updateEventTypes(
            @RequestParam String env,
            @RequestBody EventType eventType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<EventType> eventTypeList;
        try {
            request.setAttribute("requestBody", eventType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            eventTypeService.updateEventType(
                    eventType);
            eventTypeList = new ArrayList<>();
            eventTypeList.add(eventType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update eventType");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", eventTypeList);
        return eventTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEventTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long eventTypeId,
            @RequestParam(required = false) String eventTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(eventTypeId != null) {
                eventTypeService.deleteEventType(
                        eventTypeId);
            } else if (eventTypeName != null) {
                eventTypeService
                        .deleteEventTypeByEventTypeName(
                                eventTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented eventType ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete eventType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
