package com.umbrella.insurance.endpoints.rest.users.events.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.events.v1.db.EventPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.events.v1.db.jpa.EventService;
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
@RequestMapping(EventPrivilege.PATH)
public class EventRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(EventRestEndpoints.class);

    @Autowired
    private EventService eventService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Event createEvent(
            @RequestParam String env,
            @RequestBody Event event,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Event eventResponse;
        try {
            request.setAttribute("requestBody", event);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            eventResponse = eventService.saveEvent(event);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create event ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", eventResponse);
        return eventResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Event> readEvents(
            @RequestParam String env,
            @RequestParam(required = false) Long eventId,
            @RequestParam(required = false) Long sessionId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Event> eventList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (eventId != null) {
                Optional<Event> event = eventService.getEventById(eventId);
                if (event.isEmpty()) {
                    throw new NotFoundException("Unable to read event ");
                } else {
                    eventList = new ArrayList<>();
                    eventList.add(event.get());
                }
            } else if (sessionId != null) {
                Optional<Event> event = eventService.getEventBySessionId(sessionId);
                if (event.isEmpty()) {
                    throw new NotFoundException("Unable to read event ");
                } else {
                    eventList = new ArrayList<>();
                    eventList.add(event.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented event ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read event ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", eventList);
        return eventList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Event> updateEvents(
            @RequestParam String env,
            @RequestBody Event event,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Event> eventList;
        try {
            request.setAttribute("requestBody", event);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            event = eventService.updateEvent(event);
            eventList = new ArrayList<>();
            eventList.add(event);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update event ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", eventList);
        return eventList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEvents(
            @RequestParam String env,
            @RequestParam(required = false) Long eventId,
            @RequestParam(required = false) Long sessionId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(eventId != null) {
                eventService.deleteEvent(eventId);
            } else if (sessionId != null) {
                eventService.deleteBySessionId(sessionId);
            } else {
                throw new NotImplementedException("This delete query is not implemented event ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete event ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
