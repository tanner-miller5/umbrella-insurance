package com.umbrella.insurance.endpoints.rest.announcements.v1;

import com.umbrella.insurance.core.models.announcements.v1.db.AnnouncementPrivilege;
import com.umbrella.insurance.core.models.announcements.v1.db.jpa.AnnouncementService;
import com.umbrella.insurance.core.models.entities.Announcement;
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
@RequestMapping(AnnouncementPrivilege.PATH)
public class AnnouncementRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(AnnouncementRestEndpoints.class);

    @Autowired
    private AnnouncementService announcementService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Announcement createAnnouncement(
            @RequestParam String env,
            @RequestBody Announcement announcement,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Announcement announcementResponse;
        try {
            request.setAttribute("requestBody", announcement);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            announcementResponse = announcementService.saveAnnouncement(announcement);

        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create announcement");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", announcementResponse);
        return announcementResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Announcement> readAnnouncements(
            @RequestParam String env,
            @RequestParam(required = false) Long announcementId,
            @RequestParam(required = false) String announcementName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Announcement> announcementList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (announcementId != null) {
                Optional<Announcement> announcement = announcementService.getAnnouncementById(announcementId);
                if(announcement.isEmpty()) {
                    throw new NotFoundException("Unable to read announcement ");
                }
                announcementList = new ArrayList<>();
                announcementList.add(announcement.get());
            } else if (announcementName != null) {
                Optional<Announcement> announcement = announcementService.getAnnouncementByName(
                        announcementName);
                if(announcement.isEmpty()) {
                    throw new NotFoundException("Unable to read announcement ");
                } else {
                    announcementList = new ArrayList<>();
                    announcementList.add(announcement.get());
                }
            } else {
                announcementList = announcementService.getAnnouncements();
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read announcement");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", announcementList);
        return announcementList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Announcement> updateAnnouncements(
            @RequestParam String env,
            @RequestBody Announcement announcement,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Announcement> announcementList;
        try {
            request.setAttribute("requestBody", announcement);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            announcement = announcementService.updateAnnouncement(
                    announcement);
            announcementList = new ArrayList<>();
            announcementList.add(announcement);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update announcement");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", announcementList);
        return announcementList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAnnouncements(@RequestParam String env,
                                   @RequestParam(required = false) Long announcementId,
                                   @RequestParam(required = false) String announcementName,
                                   @RequestAttribute BigInteger currentRequestNumber,
                                   ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(announcementId != null) {
                announcementService.deleteAnnouncement(announcementId);

            } else if (announcementName != null) {
                announcementService.deleteAnnouncement(announcementId);
            } else {
                throw new NotImplementedException("This delete query is not implemented announcement");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete announcement");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
