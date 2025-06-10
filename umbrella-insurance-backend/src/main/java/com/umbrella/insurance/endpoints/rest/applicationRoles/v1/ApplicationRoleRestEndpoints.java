package com.umbrella.insurance.endpoints.rest.applicationRoles.v1;

import com.umbrella.insurance.core.models.applicationRoles.v1.db.ApplicationRolePrivilege;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.entities.ApplicationRole;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
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
@RequestMapping(ApplicationRolePrivilege.PATH)
public class ApplicationRoleRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationRoleRestEndpoints.class);
    
    @Autowired
    private ApplicationRoleService applicationRoleService;
    
    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ApplicationRole createApplicationRole(
            @RequestParam String env,
            @RequestBody ApplicationRole applicationRole,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            request.setAttribute("requestBody", applicationRole);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            applicationRole = applicationRoleService.saveApplicationRole(applicationRole);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create applicationRole ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", applicationRole);
        return applicationRole;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ApplicationRole> readApplicationRoles(
            @RequestParam String env,
            @RequestParam(required = false) Long applicationRoleId,
            @RequestParam(required = false) String applicationRoleName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<ApplicationRole> applicationRoleList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (applicationRoleId != null) {
                ApplicationRole applicationRole = applicationRoleService
                        .getApplicationRoleByApplicationRoleId(applicationRoleId).get();
                applicationRoleList = new ArrayList<>();
                applicationRoleList.add(applicationRole);
            } else if (applicationRoleName != null) {
                Optional<ApplicationRole> applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                        applicationRoleName);
                if(applicationRole.isEmpty()) {
                    throw new NotImplementedException("ApplicationRole not found");
                } else {
                    applicationRoleList = new ArrayList<>();
                    applicationRoleList.add(applicationRole.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented applicationRole");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read applicationRole ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", applicationRoleList);
        return applicationRoleList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ApplicationRole> updateApplicationRoles(
            @RequestParam String env,
            @RequestBody ApplicationRole applicationRole,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<ApplicationRole> applicationRoleList;
        try {
            request.setAttribute("requestBody", applicationRole);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            applicationRole = applicationRoleService.updateApplicationRole(
                    applicationRole);
            applicationRoleList = new ArrayList<>();
            applicationRoleList.add(applicationRole);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update applicationRole ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", applicationRoleList);
        return applicationRoleList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteApplicationRoles(@RequestParam String env,
                                      @RequestParam(required = false) Long applicationRoleId,
                                      @RequestParam(required = false) String applicationRoleName,
                                      @RequestAttribute BigInteger currentRequestNumber,
                                      ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(applicationRoleId != null) {
                applicationRoleService.deleteApplicationRole(applicationRoleId);
            } else if (applicationRoleName != null) {
                applicationRoleService.deleteApplicationRoleByApplicationRoleName(applicationRoleName);
            } else {
                throw new NotImplementedException("This delete query is not implemented applicationRole ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete applicationRole ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
