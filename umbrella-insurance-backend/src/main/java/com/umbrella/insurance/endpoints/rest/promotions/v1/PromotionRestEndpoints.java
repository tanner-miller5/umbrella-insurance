package com.umbrella.insurance.endpoints.rest.promotions.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.promotions.v1.db.PromotionPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.promotions.v1.db.jpa.PromotionService;
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
@RequestMapping(PromotionPrivilege.PATH)
public class PromotionRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(PromotionRestEndpoints.class);

    @Autowired
    private PromotionService promotionService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Promotion createPromotion(
            @RequestParam String env,
            @RequestBody Promotion promotion,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Promotion promotionResponse;
        try {
            request.setAttribute("requestBody", promotion);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            promotionResponse = promotionService.savePromotion(promotion);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create promotion ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", promotionResponse);
        return promotionResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Promotion> readPromotions(
            @RequestParam String env,
            @RequestParam(required = false) Long promotionId,
            @RequestParam(required = false) String promotionName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Promotion> promotionList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (promotionId != null) {
                Optional<Promotion> promotion = promotionService.getPromotion(
                        promotionId);
                if (promotion.isEmpty()) {
                    throw new NotFoundException("Unable to read promotion ");
                } else {
                    promotionList = new ArrayList<>();
                    promotionList.add(promotion.get());
                }
            } else if (promotionName != null) {
                Optional<Promotion> promotion = promotionService
                        .getPromotionByPromotionName(promotionName);
                if (promotion.isEmpty()) {
                    throw new NotFoundException("Unable to read promotion ");
                } else {
                    promotionList = new ArrayList<>();
                    promotionList.add(promotion.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented promotion ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read promotion ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", promotionList);
        return promotionList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Promotion> updatePromotions(
            @RequestParam String env,
            @RequestBody Promotion promotion,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Promotion> promotionList;
        try {
            request.setAttribute("requestBody", promotion);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            promotion = promotionService.updatePromotion(promotion);
            promotionList = new ArrayList<>();
            promotionList.add(promotion);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update promotion ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", promotionList);
        return promotionList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePromotions(
            @RequestParam String env,
            @RequestParam(required = false) Long promotionId,
            @RequestParam(required = false) String promotionName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(promotionId != null) {
                promotionService.deletePromotion(promotionId);
            } else if (promotionName != null) {
                promotionService
                        .deletePromotionByPromotionName(promotionName);
            } else {
                throw new NotImplementedException("This delete query is not implemented promotion ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete promotion ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
