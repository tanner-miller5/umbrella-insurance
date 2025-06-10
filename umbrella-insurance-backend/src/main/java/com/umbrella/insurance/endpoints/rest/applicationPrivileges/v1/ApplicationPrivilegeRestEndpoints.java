package com.umbrella.insurance.endpoints.rest.applicationPrivileges.v1;

import com.umbrella.insurance.core.models.applicationPrivileges.v1.db.ApplicationPrivilegePrivilege;
import com.umbrella.insurance.core.models.applicationPrivileges.v1.db.jpa.ApplicationPrivilegeService;
import com.umbrella.insurance.core.models.entities.ApplicationPrivilege;
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
@RequestMapping(ApplicationPrivilegePrivilege.PATH)
public class ApplicationPrivilegeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationPrivilegeRestEndpoints.class);

    @Autowired
    private ApplicationPrivilegeService applicationPrivilegeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ApplicationPrivilege createApplicationPrivilege(
            @RequestParam String env,
            @RequestBody ApplicationPrivilege applicationPrivilege,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            request.setAttribute("requestBody", applicationPrivilege);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            applicationPrivilege = applicationPrivilegeService.saveApplicationPrivilege(applicationPrivilege);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create applicationPrivilege ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", applicationPrivilege);
        return applicationPrivilege;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ApplicationPrivilege> readApplicationPrivileges(
            @RequestParam String env,
            @RequestParam(required = false) Long applicationPrivilegeId,
            @RequestParam(required = false) String applicationPrivilegeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<ApplicationPrivilege> applicationPrivilegeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (applicationPrivilegeId != null) {
                Optional<ApplicationPrivilege> applicationPrivilege = applicationPrivilegeService
                        .findApplicationPrivilegeById(applicationPrivilegeId);
                if(applicationPrivilege.isEmpty()) {
                    throw new NotFoundException("Unable to read application privilege record");
                } else {
                    applicationPrivilegeList = new ArrayList<>();
                    applicationPrivilegeList.add(applicationPrivilege.get());
                }
            } else if (applicationPrivilegeName != null) {
                Optional<ApplicationPrivilege> applicationPrivilege = applicationPrivilegeService
                        .findApplicationPrivilegeByApplicationPrivilegeName(
                        applicationPrivilegeName);
                if(applicationPrivilege.isEmpty()) {
                    throw new NotFoundException("Unable to read application privilege record");
                } else {
                    applicationPrivilegeList = new ArrayList<>();
                    applicationPrivilegeList.add(applicationPrivilege.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented applicationPrivilege ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read applicationPrivilege ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", applicationPrivilegeList);
        return applicationPrivilegeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ApplicationPrivilege> updateApplicationPrivileges(
            @RequestParam String env,
            @RequestBody ApplicationPrivilege applicationPrivilege,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<ApplicationPrivilege> applicationPrivilegeList;
        try {
            request.setAttribute("requestBody", applicationPrivilege);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            applicationPrivilege = applicationPrivilegeService.updateApplicationPrivilege(
                    applicationPrivilege);
            applicationPrivilegeList = new ArrayList<>();
            applicationPrivilegeList.add(applicationPrivilege);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update applicationPrivilege ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", applicationPrivilegeList);
        return applicationPrivilegeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteApplicationPrivileges(@RequestParam String env,
                                           @RequestParam(required = false) Long applicationPrivilegeId,
                                           @RequestParam(required = false) String applicationPrivilegeName,
                                           @RequestAttribute BigInteger currentRequestNumber,
                                           ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(applicationPrivilegeId != null) {
                applicationPrivilegeService.deleteApplicationPrivilege(applicationPrivilegeId);
            } else if (applicationPrivilegeName != null) {
                applicationPrivilegeService.deleteApplicationPrivilegeByApplicationPrivilegeName(applicationPrivilegeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented applicationPrivilege ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete applicationPrivilege ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
