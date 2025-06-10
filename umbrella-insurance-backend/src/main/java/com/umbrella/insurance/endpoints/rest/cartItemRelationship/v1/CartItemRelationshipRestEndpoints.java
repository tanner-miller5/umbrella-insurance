package com.umbrella.insurance.endpoints.rest.cartItemRelationship.v1;

import com.umbrella.insurance.core.models.cartItemRelationships.v1.db.CartItemRelationshipPrivilege;
import com.umbrella.insurance.core.models.cartItemRelationships.v1.db.jpa.CartItemRelationshipService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.CartItemRelationship;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
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
@RequestMapping(CartItemRelationshipPrivilege.PATH)
public class CartItemRelationshipRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(CartItemRelationshipRestEndpoints.class);

    @Autowired
    private CartItemRelationshipService cartItemRelationshipService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    CartItemRelationship createCartItemRelationship(@RequestParam String env,
                                @RequestBody CartItemRelationship cartItemRelationship,
                                @RequestAttribute BigInteger currentRequestNumber,
                                ServletRequest request) throws Exception {
        Connection connection = null;
        CartItemRelationship cartItemRelationshipResponse;
        try {
            request.setAttribute("requestBody", cartItemRelationship);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            cartItemRelationshipResponse = cartItemRelationshipService
                    .saveCartItemRelationship(cartItemRelationship);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create cart item relationship");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cartItemRelationshipResponse);
        return cartItemRelationshipResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<CartItemRelationship> readCartItemRelationships(@RequestParam String env,
                                     @RequestParam(required = false) Long cartItemRelationshipId,
                                     @RequestHeader String session,
                                     @RequestParam(required = false) Long itemId,
                                     @RequestParam(required = false) Long cartId,
                                     @RequestAttribute BigInteger currentRequestNumber,
                                     ServletRequest request) throws Exception {
        Connection connection = null;
        List<CartItemRelationship> cartItemRelationshipList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(cartItemRelationshipId != null) {
                Optional<CartItemRelationship> cartItemRelationship = cartItemRelationshipService
                        .getCartItemRelationshipById(cartItemRelationshipId);
                if(cartItemRelationship.isEmpty()) {
                    throw new NotFoundException("Unable to find cart item relationship");
                } else {
                    cartItemRelationshipList = new ArrayList<>();
                    cartItemRelationshipList.add(cartItemRelationship.get());
                }
            } else if (cartId != null && itemId != null) {
                Optional<CartItemRelationship> cartItemRelationship = cartItemRelationshipService
                        .getCartItemRelationshipByCartIdAndItemId(cartId, itemId);
                if(cartItemRelationship.isEmpty()) {
                    throw new NotFoundException("Unable to find cart item relationship");
                } else {
                    cartItemRelationshipList = new ArrayList<>();
                    cartItemRelationshipList.add(cartItemRelationship.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented for cart item relationship");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read cart item relationship");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cartItemRelationshipList);
        return cartItemRelationshipList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<CartItemRelationship> updateCartItemRelationships(@RequestParam String env,
                                       @RequestBody CartItemRelationship cartItemRelationship,
                                       @RequestAttribute BigInteger currentRequestNumber,
                                       ServletRequest request) throws Exception {
        Connection connection = null;
        List<CartItemRelationship> cartItemRelationshipList;
        try {
            request.setAttribute("requestBody", cartItemRelationship);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            cartItemRelationship = cartItemRelationshipService.updateCartItemRelationship(cartItemRelationship);
            cartItemRelationshipList = new ArrayList<>();
            cartItemRelationshipList.add(cartItemRelationship);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update cart item relationship");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cartItemRelationshipList);
        return cartItemRelationshipList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCartItemRelationships(@RequestParam String env,
                                     @RequestParam(required = false) Long cartItemRelationshipId,
                                     @RequestParam(required = false) Long itemId,
                                     @RequestParam(required = false) Long cartId,
                                     @RequestAttribute BigInteger currentRequestNumber,
                                     ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (cartItemRelationshipId != null) {
                cartItemRelationshipService.deleteCartItemRelationship(cartItemRelationshipId);
            } else if (itemId != null && cartId != null) {
                cartItemRelationshipService.deleteCartItemRelationshipByCartIdAndItemId(
                        cartId, itemId);
            } else {
                throw new NotImplementedException("This delete query is not implemented cart item relationship");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete cart item relationship");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
