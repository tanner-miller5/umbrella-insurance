package com.umbrella.insurance.endpoints.rest.ads.v1;

import com.umbrella.insurance.core.models.ads.v1.db.AdPrivilege;
import com.umbrella.insurance.core.models.ads.v1.db.jpa.AdService;
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

import com.umbrella.insurance.core.models.entities.Ad;

@Controller
@RequestMapping(AdPrivilege.PATH)
public class AdRestEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(AdRestEndpoints.class);

    @Autowired
    private AdService adService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Ad createAd(@RequestParam String env,
                            @RequestBody Ad ad,
                            @RequestAttribute BigInteger currentRequestNumber,
                            ServletRequest request) throws Exception {
        Connection connection = null;
        Ad adResponse;
        try {
            request.setAttribute("requestBody", ad);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            adResponse = adService.saveAd(ad);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create ad record");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", adResponse);
        return adResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Ad> readAds(@RequestParam String env,
                                 @RequestParam(required = false) Long adId,
                                 @RequestParam(required = false) String adName,
                                 @RequestAttribute BigInteger currentRequestNumber,
                                 ServletRequest request) throws Exception {
        Connection connection = null;
        List<Ad> adList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(adId != null) {
                Optional<Ad> ad = adService.findByAdId(adId);
                if (ad.isEmpty()) {
                    throw new NotFoundException("Unable to read ad record");
                } else {
                    adList = new ArrayList<>();
                    adList.add(ad.get());
                }
            } else if (adName != null) {
                Optional<Ad> ad = adService.findByAdName(adName);
                if (ad.isEmpty()) {
                    throw new NotFoundException("Unable to read ad record");
                } else {
                    adList = new ArrayList<>();
                    adList.add(ad.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented ad record");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read ad record");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", adList);
        return adList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Ad> updateAds(@RequestParam String env,
                       @RequestBody Ad ad,
                       @RequestAttribute BigInteger currentRequestNumber,
                       ServletRequest request) throws Exception {
        Connection connection = null;
        List<Ad> adList;
        try {
            request.setAttribute("requestBody", ad);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            ad = adService.updateAd(ad);
            adList = new ArrayList<>();
            adList.add(ad);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update ad record");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", adList);
        return adList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAds(@RequestParam String env,
                         @RequestParam(required = false) Long adId,
                         @RequestParam(required = false) String adName,
                         @RequestAttribute BigInteger currentRequestNumber,
                         ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (adId != null) {
                adService.deleteByAdId(adId);
            } else if (adName != null) {
                adService.deleteByAdName(adName);
            } else {
                throw new NotImplementedException("This delete query is not implemented ad record");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete ad record");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
