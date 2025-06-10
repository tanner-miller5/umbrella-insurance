package com.umbrella.insurance.endpoints.rest.groups.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.groups.v1.db.GroupPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.groups.v1.db.jpa.GroupService;
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
@RequestMapping(GroupPrivilege.PATH)
public class GroupRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(GroupRestEndpoints.class);

    @Autowired
    private GroupService groupService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Group createGroup(
            @RequestParam String env,
            @RequestBody Group group,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Group groupResponse;
        try {
            request.setAttribute("requestBody", group);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            groupResponse = groupService.saveGroup(group);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create group ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", groupResponse);
        return groupResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Group> readGroups(
            @RequestParam String env,
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) String groupName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Group> groupList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (groupId != null) {
                Optional<Group> group = groupService.getGroupByGroupId(groupId);
                if (group.isEmpty()) {
                    throw new NotFoundException("Unable to read group ");
                } else {
                    groupList = new ArrayList<>();
                    groupList.add(group.get());
                }
            } else if (groupName != null) {
                Optional<Group> group = groupService
                        .getGroupByGroupName(groupName);
                if (group.isEmpty()) {
                    throw new NotFoundException("Unable to read group ");
                } else {
                    groupList = new ArrayList<>();
                    groupList.add(group.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented group ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read group ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", groupList);
        return groupList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Group> updateGroups(
            @RequestParam String env,
            @RequestBody Group group,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Group> groupList;
        try {
            request.setAttribute("requestBody", group);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            group = groupService.updateGroup(group);
            groupList = new ArrayList<>();
            groupList.add(group);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update group ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", groupList);
        return groupList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGroups(
            @RequestParam String env,
            @RequestParam(required = false) Long groupId,
            @RequestParam(required = false) String groupName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(groupId != null) {
                groupService.deleteGroup(
                        groupId);
            } else if (groupName != null) {
                groupService
                        .deleteGroupByGroupName(
                                groupName);
            } else {
                throw new NotImplementedException("This delete query is not implemented group ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete group ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
