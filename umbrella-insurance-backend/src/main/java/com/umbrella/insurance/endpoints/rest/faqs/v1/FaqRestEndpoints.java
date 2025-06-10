package com.umbrella.insurance.endpoints.rest.faqs.v1;

import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.faqs.v1.db.FaqPrivilege;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.faqs.v1.db.jpa.FaqService;
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
@RequestMapping(FaqPrivilege.PATH)
public class FaqRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(FaqRestEndpoints.class);

    @Autowired
    private FaqService faqService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Faq createFaq(
            @RequestParam String env,
            @RequestBody Faq faq,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Faq faqResponse;
        try {
            request.setAttribute("requestBody", faq);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            faqResponse = faqService.saveFaq(faq);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create faq ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", faqResponse);
        return faqResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Faq> readFaqs(
            @RequestParam String env,
            @RequestParam(required = false) Long faqId,
            @RequestParam(required = false) String faqName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Faq> faqList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (faqId != null) {
                Optional<Faq> faq = faqService.getFaq(faqId);
                if(faq.isEmpty()) {
                    throw new NotFoundException("Faq not found");
                } else {
                    faqList = new ArrayList<>();
                    faqList.add(faq.get());
                }
            } else if (faqName != null) {
                Optional<Faq> faq = faqService.getFaqByName(faqName);
                if(faq.isEmpty()) {
                    throw new NotFoundException("Faq not found");
                } else {
                    faqList = new ArrayList<>();
                    faqList.add(faq.get());
                }
            } else {
                faqList = faqService.getFaqs();
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read faq ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", faqList);
        return faqList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Faq> updateFaqs(
            @RequestParam String env,
            @RequestBody Faq faq,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Faq> faqList;
        try {
            request.setAttribute("requestBody", faq);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            faq = faqService.updateFaq(
                    faq);
            faqList = new ArrayList<>();
            faqList.add(faq);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update faq ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", faqList);
        return faqList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteFaqs(@RequestParam String env,
                                   @RequestParam(required = false) Long faqId,
                                   @RequestParam(required = false) String faqName,
                                   @RequestAttribute BigInteger currentRequestNumber,
                                   ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(faqId != null) {
                faqService.deleteFaq(faqId);
            } else if (faqName != null) {
                faqService.deleteFaqByName(faqName);
            } else {
                throw new NotImplementedException("This delete query is not implemented faq ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete faq ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
