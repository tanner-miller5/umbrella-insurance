package com.umbrella.insurance.endpoints.rest.users.sessions.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.sessions.v1.db.SessionPrivilege;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
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
@RequestMapping(SessionPrivilege.PATH)
public class SessionRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(SessionRestEndpoints.class);

    @Autowired
    private SessionService sessionService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Session createSession(
            @RequestParam String env,
            @RequestBody Session session,
            @RequestAttribute BigInteger currentRequestNumber,
            @RequestAttribute Device device,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Session sessionResponse;
        try {
            request.setAttribute("requestBody", session);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            sessionResponse = sessionService.saveSession(session);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create session ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", sessionResponse);
        return sessionResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Session> readSessions(
            @RequestParam String env,
            @RequestParam(required = false) Long sessionId,
            @RequestParam(required = false) String sessionCode,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Session> sessionList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (sessionId != null) {
                Optional<Session> session = sessionService.getSessionBySessionId(
                        sessionId);
                if (session.isEmpty()) {
                    throw new NotFoundException("Unable to read session");
                } else {
                    sessionList = new ArrayList<>();
                    sessionList.add(session.get());
                }
            } else if (sessionCode != null) {
                Optional<Session> session = sessionService
                        .getSessionBySessionCode(sessionCode);
                if (session.isEmpty()) {
                    throw new NotFoundException("Unable to read session");
                } else {
                    sessionList = new ArrayList<>();
                    sessionList.add(session.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented session");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read session ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", sessionList);
        return sessionList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Session> updateSessions(
            @RequestParam String env,
            @RequestBody Session session,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Session> sessionList;
        try {
            request.setAttribute("requestBody", session);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            session = sessionService.updateSession(session);
            sessionList = new ArrayList<>();
            sessionList.add(session);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update session ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", sessionList);
        return sessionList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSessions(
            @RequestParam String env,
            @RequestParam(required = false) Long sessionId,
            @RequestParam(required = false) String sessionCode,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(sessionId != null) {
                sessionService.deleteSession(
                        sessionId);
            } else if (sessionCode != null) {
                sessionService.deleteSessionBySessionCode(
                                sessionCode);
            } else {
                throw new NotImplementedException("This delete query is not implemented session ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete session ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
