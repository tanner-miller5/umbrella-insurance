package com.umbrella.insurance.endpoints.rest.users.paymentTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.paymentTypes.v1.db.PaymentTypePrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.paymentTypes.v1.db.jpa.PaymentTypeService;
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
@RequestMapping(PaymentTypePrivilege.PATH)
public class PaymentTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(PaymentTypeRestEndpoints.class);

    @Autowired
    private PaymentTypeService paymentTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    PaymentType createPaymentType(
            @RequestParam String env,
            @RequestBody PaymentType paymentType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        PaymentType paymentTypeResponse;
        try {
            request.setAttribute("requestBody", paymentType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            paymentTypeResponse = paymentTypeService.savePaymentType(paymentType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create paymentType");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", paymentTypeResponse);
        return paymentTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PaymentType> readPaymentTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long paymentTypeId,
            @RequestParam(required = false) String paymentTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PaymentType> paymentTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (paymentTypeId != null) {
                Optional<PaymentType> paymentType = paymentTypeService.findPaymentTypeByPaymentTypeId(paymentTypeId);
                if (paymentType.isEmpty()) {
                    throw new NotFoundException("Unable to read paymentType ");
                } else {
                    paymentTypeList = new ArrayList<>();
                    paymentTypeList.add(paymentType.get());
                }
            } else if (paymentTypeName != null) {
                Optional<PaymentType> paymentType = paymentTypeService.findPaymentTypeByPaymentTypeName(paymentTypeName);
                if (paymentType.isEmpty()) {
                    throw new NotFoundException("Unable to read paymentType ");
                } else {
                    paymentTypeList = new ArrayList<>();
                    paymentTypeList.add(paymentType.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented paymentType ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read paymentType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", paymentTypeList);
        return paymentTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<PaymentType> updatePaymentTypes(
            @RequestParam String env,
            @RequestBody PaymentType paymentType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<PaymentType> paymentTypeList;
        try {
            request.setAttribute("requestBody", paymentType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            paymentType = paymentTypeService.updatePaymentType(paymentType);
            paymentTypeList = new ArrayList<>();
            paymentTypeList.add(paymentType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update paymentType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", paymentTypeList);
        return paymentTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePaymentTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long paymentTypeId,
            @RequestParam(required = false) String paymentTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(paymentTypeId != null) {
                paymentTypeService.deletePaymentType(
                        paymentTypeId);
            } else if (paymentTypeName != null) {
                paymentTypeService.deletePaymentTypeByPaymentTypeName(paymentTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented paymentType ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete paymentType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
