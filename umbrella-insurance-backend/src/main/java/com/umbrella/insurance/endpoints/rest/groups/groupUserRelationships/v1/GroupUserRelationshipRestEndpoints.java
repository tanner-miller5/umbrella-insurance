package com.umbrella.insurance.endpoints.rest.groups.groupUserRelationships.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.groups.groupUserRelationships.v1.db.GroupUserRelationshipPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.groups.groupUserRelationships.v1.db.jpa.GroupUserRelationshipService;
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
@RequestMapping(GroupUserRelationshipPrivilege.PATH)
public class GroupUserRelationshipRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(GroupUserRelationshipRestEndpoints.class);

    @Autowired
    private GroupUserRelationshipService groupUserRelationshipService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    GroupUserRelationship createGroupUserRelationship(
            @RequestParam String env,
            @RequestBody GroupUserRelationship groupUserRelationship,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        GroupUserRelationship groupUserRelationshipResponse;
        try {
            request.setAttribute("requestBody", groupUserRelationship);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            groupUserRelationshipResponse = groupUserRelationshipService.saveGroupUserRelationship(
                    groupUserRelationship);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create groupUserRelationship ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", groupUserRelationshipResponse);
        return groupUserRelationshipResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<GroupUserRelationship> readGroupUserRelationships(
            @RequestParam String env,
            @RequestParam(required = false) Long groupUserRelationshipId,
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<GroupUserRelationship> groupUserRelationshipList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (groupUserRelationshipId != null) {
                Optional<GroupUserRelationship> groupUserRelationship = groupUserRelationshipService.getGroupUserRelationshipById(
                        groupUserRelationshipId);
                if (groupUserRelationship.isEmpty()) {
                    throw new NotFoundException("Unable to read groupUserRelationship ");
                } else {
                    groupUserRelationshipList = new ArrayList<>();
                    groupUserRelationshipList.add(groupUserRelationship.get());
                }
            } else if (groupId != null && userId != null) {
                Optional<GroupUserRelationship> groupUserRelationship = groupUserRelationshipService
                        .getGroupUserRelationshipByGroupIdAndUserId(
                                groupId,
                                userId);
                if (groupUserRelationship.isEmpty()) {
                    throw new NotFoundException("Unable to read groupUserRelationship ");
                } else {
                    groupUserRelationshipList = new ArrayList<>();
                    groupUserRelationshipList.add(groupUserRelationship.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented groupUserRelationship ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read groupUserRelationship ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", groupUserRelationshipList);
        return groupUserRelationshipList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<GroupUserRelationship> updateGroupUserRelationships(
            @RequestParam String env,
            @RequestBody GroupUserRelationship groupUserRelationship,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<GroupUserRelationship> groupUserRelationshipList;
        try {
            request.setAttribute("requestBody", groupUserRelationship);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            groupUserRelationship = groupUserRelationshipService.updateGroupUserRelationship(
                    groupUserRelationship);
            groupUserRelationshipList = new ArrayList<>();
            groupUserRelationshipList.add(groupUserRelationship);

        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update groupUserRelationship ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", groupUserRelationshipList);
        return groupUserRelationshipList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGroupUserRelationships(
            @RequestParam String env,
            @RequestParam(required = false) Long groupUserRelationshipId,
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) Long userId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(groupUserRelationshipId != null) {
                groupUserRelationshipService.deleteGroupUserRelationship(
                        groupUserRelationshipId);
            } else if (groupId != null && userId != null) {
                groupUserRelationshipService
                        .deleteGroupUserRelationshipByGroupIdAndUserId(
                        groupId, userId);
            } else {
                throw new NotImplementedException("This delete query is not implemented groupUserRelationship ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete groupUserRelationship ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
