package com.umbrella.insurance.endpoints.rest.leagues.conferences.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.leagues.conferences.v1.db.ConferencePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.leagues.conferences.v1.db.jpa.ConferenceService;
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
@RequestMapping(ConferencePrivilege.PATH)
public class ConferenceRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(ConferenceRestEndpoints.class);

    @Autowired
    private ConferenceService conferenceService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Conference createConference(
            @RequestParam String env,
            @RequestBody Conference conference,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Conference conferenceResponse;
        try {
            request.setAttribute("requestBody", conference);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            conferenceResponse = conferenceService.saveConference(conference);

        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create conference ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", conferenceResponse);
        return conferenceResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Conference> readConferences(
            @RequestParam String env,
            @RequestParam(required = false) Long conferenceId,
            @RequestParam(required = false) String conferenceName,
            @RequestParam(required = false) Long leagueId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Conference> conferenceList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (conferenceId != null) {
                Optional<Conference> conference = conferenceService.getConferenceById(
                        conferenceId);
                if (conference == null) {
                    throw new NotFoundException("Unable to read conference ");
                } else {
                    conferenceList = new ArrayList<>();
                    conferenceList.add(conference.get());
                }
            } else if (conferenceName != null) {
                Optional<Conference> conference = conferenceService
                        .getConferenceByNameAndLeagueId(
                                conferenceName,
                                leagueId);
                if (conference.isEmpty()) {
                    throw new NotFoundException("Unable to read conference ");
                } else {
                    conferenceList = new ArrayList<>();
                    conferenceList.add(conference.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented conference ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read conference ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", conferenceList);
        return conferenceList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Conference> updateConferences(
            @RequestParam String env,
            @RequestBody Conference conference,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Conference> conferenceList;
        try {
            request.setAttribute("requestBody", conference);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            conference = conferenceService.updateConference(conference);
            conferenceList = new ArrayList<>();
            conferenceList.add(conference);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update conference ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", conferenceList);
        return conferenceList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteConferences(
            @RequestParam String env,
            @RequestParam(required = false) Long conferenceId,
            @RequestParam(required = false) String conferenceName,
            @RequestParam(required = false) Long leagueId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(conferenceId != null) {
                conferenceService.deleteConference(conferenceId);
            } else if (conferenceName != null) {
                conferenceService.deleteConferenceByConferenceNameAndLeagueId(
                                conferenceName, leagueId);
            } else {
                throw new NotImplementedException("This delete query is not implemented conference ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete conference ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
