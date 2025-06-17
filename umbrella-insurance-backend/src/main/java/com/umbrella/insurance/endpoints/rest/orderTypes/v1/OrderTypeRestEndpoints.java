package com.umbrella.insurance.endpoints.rest.orderTypes.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.OrderType;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.orderTypes.v1.db.OrderTypePrivilege;
import com.umbrella.insurance.core.models.orderTypes.v1.db.jpa.OrderTypeService;
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
@RequestMapping(OrderTypePrivilege.PATH)
public class OrderTypeRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(OrderTypeRestEndpoints.class);

    @Autowired
    private OrderTypeService orderTypeService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    OrderType createOrderType(
            @RequestParam String env,
            @RequestBody OrderType orderType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        OrderType orderTypeResponse;
        try {
            request.setAttribute("requestBody", orderType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            orderTypeResponse = orderTypeService.saveOrderType(orderType);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create orderType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", orderTypeResponse);
        return orderTypeResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<OrderType> readOrderTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long orderTypeId,
            @RequestParam(required = false) String orderTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<OrderType> orderTypeList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (orderTypeId != null) {
                Optional<OrderType> orderType = orderTypeService.getOrderTypeById(
                        orderTypeId);
                if (orderType.isEmpty()) {
                    throw new NotFoundException("Unable to read orderType ");
                } else {
                    orderTypeList = new ArrayList<>();
                    orderTypeList.add(orderType.get());
                }
            } else if (orderTypeName != null) {
                Optional<OrderType> orderType = orderTypeService
                        .getOrderTypeByOrderTypeName(orderTypeName);
                if (orderType.isEmpty()) {
                    throw new NotFoundException("Unable to read orderType ");
                } else {
                    orderTypeList = new ArrayList<>();
                    orderTypeList.add(orderType.get());
                }
            } else {
                orderTypeList = orderTypeService.getOrderTypes();
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read orderType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", orderTypeList);
        return orderTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<OrderType> updateOrderTypes(
            @RequestParam String env,
            @RequestBody OrderType orderType,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<OrderType> orderTypeList;
        try {
            request.setAttribute("requestBody", orderType);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            orderType = orderTypeService.updateOrderType(
                    orderType);
            orderTypeList = new ArrayList<>();
            orderTypeList.add(orderType);

        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update orderType ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", orderTypeList);
        return orderTypeList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrderTypes(
            @RequestParam String env,
            @RequestParam(required = false) Long orderTypeId,
            @RequestParam(required = false) String orderTypeName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(orderTypeId != null) {
                orderTypeService.deleteOrderType(
                        orderTypeId);
            } else if (orderTypeName != null) {
                orderTypeService
                        .deleteOrderTypeByOrderTypeName(
                                orderTypeName);
            } else {
                throw new NotImplementedException("This delete query is not implemented orderType ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete orderType ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
