package com.umbrella.insurance.endpoints.rest.applicationRoleApplicationPrivilegeRelationships.v1;

import com.umbrella.insurance.core.models.applicationRoleApplicationPrivilegeRelationships.v1.db.ApplicationRoleApplicationPrivilegeRelationshipPrivilege;
import com.umbrella.insurance.core.models.applicationRoleApplicationPrivilegeRelationships.v1.db.jpa.ApplicationRoleApplicationPrivilegeRelationshipService;
import com.umbrella.insurance.core.models.entities.ApplicationRoleApplicationPrivilegeRelationship;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
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
@RequestMapping(ApplicationRoleApplicationPrivilegeRelationshipPrivilege.PATH)
public class ApplicationRoleApplicationPrivilegeRelationshipRestEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(
            ApplicationRoleApplicationPrivilegeRelationshipRestEndpoints.class);
    
    @Autowired
    private ApplicationRoleApplicationPrivilegeRelationshipService applicationRoleApplicationPrivilegeRelationshipService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ApplicationRoleApplicationPrivilegeRelationship createApplicationRoleApplicationPrivilegeRelationship(
            @RequestParam String env,
            @RequestBody ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            applicationRoleApplicationPrivilegeRelationship = applicationRoleApplicationPrivilegeRelationshipService
                    .saveApplicationRoleApplicationPrivilegeRelationship(
                    applicationRoleApplicationPrivilegeRelationship);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create applicationRoleApplicationPrivilegeRelationship ");
        } finally {
            Database.closeConnection(connection);
        }
        return applicationRoleApplicationPrivilegeRelationship;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ApplicationRoleApplicationPrivilegeRelationship> readApplicationRoleApplicationPrivilegeRelationships(
            @RequestParam String env,
            @RequestParam(required = false) Long applicationRoleId,
            @RequestParam(required = false) Long applicationPrivilegeId,
            @RequestParam(required = false) Long applicationRoleApplicationPrivilegeRelationshipId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request
            ) throws Exception {
        Connection connection = null;
        List<ApplicationRoleApplicationPrivilegeRelationship> applicationRoleApplicationPrivilegeRelationshipList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (applicationRoleId != null && applicationPrivilegeId == null) {
                applicationRoleApplicationPrivilegeRelationshipList = applicationRoleApplicationPrivilegeRelationshipService
                        .findApplicationRoleApplicationPrivilegeRelationshipsByApplicationRoleId(
                                applicationRoleId);
                if (applicationRoleApplicationPrivilegeRelationshipList.isEmpty()) {
                    throw new NotFoundException("Unable to read applicationRoleApplicationPrivilegeRelationship ");
                }
            } else if (applicationRoleId != null && applicationPrivilegeId != null) {
                Optional<ApplicationRoleApplicationPrivilegeRelationship> applicationRoleApplicationPrivilegeRelationship = applicationRoleApplicationPrivilegeRelationshipService
                        .findApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegeId(
                                applicationRoleId, applicationPrivilegeId);
                if(applicationRoleApplicationPrivilegeRelationship.isEmpty()) {
                    throw new NotFoundException("Unable to read applicationRoleApplicationPrivilegeRelationship ");
                } else {
                    applicationRoleApplicationPrivilegeRelationshipList = new ArrayList<>();
                    applicationRoleApplicationPrivilegeRelationshipList.add(applicationRoleApplicationPrivilegeRelationship.get());
                }
            } else if (applicationRoleApplicationPrivilegeRelationshipId != null){
                Optional<ApplicationRoleApplicationPrivilegeRelationship> applicationRoleApplicationPrivilegeRelationship =
                        applicationRoleApplicationPrivilegeRelationshipService
                        .findApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleApplicationPrivilegeRelationshipId(
                                applicationRoleApplicationPrivilegeRelationshipId);
                if (applicationRoleApplicationPrivilegeRelationship.isEmpty()) {
                    throw new NotFoundException("Unable to read applicationRoleApplicationPrivilegeRelationship ");
                } else {
                    applicationRoleApplicationPrivilegeRelationshipList = new ArrayList<>();
                    applicationRoleApplicationPrivilegeRelationshipList.add(applicationRoleApplicationPrivilegeRelationship.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented applicationRoleApplicationPrivilegeRelationship ");
            }
        } catch(NotFoundException | NotImplementedException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read applicationRoleApplicationPrivilegeRelationship ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", applicationRoleApplicationPrivilegeRelationshipList);
        return applicationRoleApplicationPrivilegeRelationshipList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ApplicationRoleApplicationPrivilegeRelationship> updateApplicationRoleApplicationPrivilegeRelationships(
            @RequestParam String env,
            @RequestBody ApplicationRoleApplicationPrivilegeRelationship applicationRoleApplicationPrivilegeRelationship,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<ApplicationRoleApplicationPrivilegeRelationship> applicationRoleApplicationPrivilegeRelationshipList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            applicationRoleApplicationPrivilegeRelationship = applicationRoleApplicationPrivilegeRelationshipService
                    .updateApplicationRoleApplicationPrivilegeRelationship(
                    applicationRoleApplicationPrivilegeRelationship);
            applicationRoleApplicationPrivilegeRelationshipList = new ArrayList<>();
            applicationRoleApplicationPrivilegeRelationshipList.add(applicationRoleApplicationPrivilegeRelationship);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update applicationRoleApplicationPrivilegeRelationship ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", applicationRoleApplicationPrivilegeRelationship);
        return applicationRoleApplicationPrivilegeRelationshipList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteApplicationRoleApplicationPrivilegeRelationships(
            @RequestParam String env,
            @RequestParam(required = false) Long applicationRoleId,
            @RequestParam(required = false) Long applicationPrivilegeId,
            @RequestParam(required = false) Long applicationRoleApplicationPrivilegeRelationshipId,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (applicationRoleApplicationPrivilegeRelationshipId != null) {
                applicationRoleApplicationPrivilegeRelationshipService
                        .deleteApplicationRoleApplicationPrivilegeRelationship(
                                applicationRoleApplicationPrivilegeRelationshipId);
            } else if (applicationRoleId != null && applicationPrivilegeId != null) {
                applicationRoleApplicationPrivilegeRelationshipService
                        .deleteByApplicationRoleIdAndApplicationPrivilegeId(
                                applicationRoleId,
                                applicationPrivilegeId);
            } else {
                throw new NotImplementedException("This delete query is not implemented applicationRoleApplicationPrivilegeRelationship ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete applicationRoleApplicationPrivilegeRelationship ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
