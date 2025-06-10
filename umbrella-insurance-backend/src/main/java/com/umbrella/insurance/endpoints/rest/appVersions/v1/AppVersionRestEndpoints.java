package com.umbrella.insurance.endpoints.rest.appVersions.v1;

import com.umbrella.insurance.core.models.appVersions.v1.db.AppVersionPrivilege;
import com.umbrella.insurance.core.models.appVersions.v1.db.jpa.AppVersionService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.AppVersion;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import jakarta.servlet.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping(AppVersionPrivilege.PATH)
public class AppVersionRestEndpoints {

    @Value("${info.app.version}")
    private String applicationVersion;
    @Value("${info.app.name}")
    private String applicationName;

    private static final Logger logger = LoggerFactory.getLogger(AppVersionRestEndpoints.class);

    @Autowired
    private AppVersionService appVersionService;

    @Autowired
    private SessionService sessionService;
    
    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    AppVersion createAppVersion(@RequestParam String env,
                                      @RequestBody AppVersion appVersion,
                                      @RequestAttribute BigInteger currentRequestNumber,
                                      ServletRequest request) throws Exception {
        Connection connection = null;
        AppVersion appVersionResponse;
        try {
            request.setAttribute("requestBody", appVersion);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            appVersionResponse = appVersionService.saveAppVersion(appVersion);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create app version ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", appVersionResponse);
        return appVersionResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AppVersion> readAppVersions(@RequestParam String env,
                                 @RequestParam(required = false) Long appVersionId,
                                 @RequestHeader String session,
                                 @RequestParam(required = false) String appName,
                                 @RequestAttribute BigInteger currentRequestNumber,
                                 ServletRequest request) throws Exception {
        Connection connection = null;
        List<AppVersion> appVersionList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(appVersionId != null) {
                Optional<AppVersion> appVersion = appVersionService.getAppVersionById(appVersionId);
                if(appVersion.isEmpty()) {
                    throw new NotFoundException("Unable to find app version");
                } else {
                    appVersionList = new ArrayList<>();
                    appVersionList.add(appVersion.get());
                }
            } else if (appName != null) {
                Optional<AppVersion> appVersion = appVersionService.getAppVersionByAppName(appName);
                if(appVersion.isEmpty()) {
                    throw new NotFoundException("Unable to find app version");
                } else {
                    appVersionList = new ArrayList<>();
                    appVersionList.add(appVersion.get());
                }
            } else {
                AppVersion appVersion = new AppVersion();
                appVersion.setAppName(applicationName);
                appVersion.setAppVersion(applicationVersion);
                appVersionList = new ArrayList<>();
                appVersionList.add(appVersion);
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read appVersion ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", appVersionList);
        return appVersionList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AppVersion> updateAppVersions(@RequestParam String env,
                                   @RequestBody AppVersion appVersion,
                                   @RequestAttribute BigInteger currentRequestNumber,
                                   ServletRequest request) throws Exception {
        Connection connection = null;
        List<AppVersion> appVersionList;
        try {
            request.setAttribute("requestBody", appVersion);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            appVersion = appVersionService.updateAppVersion(appVersion);
            appVersionList = new ArrayList<>();
            appVersionList.add(appVersion);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update app version ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", appVersionList);
        return appVersionList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAppVersions(@RequestParam String env,
                         @RequestParam(required = false) Long appVersionId,
                         @RequestParam(required = false) String appName,
                         @RequestAttribute BigInteger currentRequestNumber,
                         ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (appVersionId != null) {
                appVersionService.deleteAppVersion(appVersionId);
            } else if (appName != null) {
                appVersionService.deleteAppVersionByAppName(appName);
            } else {
                throw new NotImplementedException("This delete query is not implemented app version ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete app version ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
