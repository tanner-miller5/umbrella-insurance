package com.umbrella.insurance.endpoints.rest.carts.v1;

import com.umbrella.insurance.core.models.carts.v1.db.CartPrivilege;
import com.umbrella.insurance.core.models.carts.v1.db.jpa.CartService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Cart;
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
@RequestMapping(CartPrivilege.PATH)
public class CartRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(CartRestEndpoints.class);

    @Autowired
    private CartService cartService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Cart createCart(@RequestParam String env,
                                    @RequestBody Cart cart,
                                    @RequestAttribute BigInteger currentRequestNumber,
                                    ServletRequest request) throws Exception {
        Connection connection = null;
        Cart cartResponse;
        try {
            request.setAttribute("requestBody", cart);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            cartResponse = cartService
                    .saveCart(cart);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create cart");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cartResponse);
        return cartResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Cart> readCarts(@RequestParam String env,
                                 @RequestParam(required = false) Long cartId,
                                 @RequestHeader String session,
                                 @RequestParam(required = false) Long userId,
                                 @RequestAttribute BigInteger currentRequestNumber,
                                 ServletRequest request) throws Exception {
        Connection connection = null;
        List<Cart> cartList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(cartId != null) {
                Optional<Cart> cart = cartService
                        .getCartById(cartId);
                if(cart.isEmpty()) {
                    throw new NotFoundException("Unable to find cart");
                } else {
                    cartList = new ArrayList<>();
                    cartList.add(cart.get());
                }
            } else if (userId != null) {
                Optional<Cart> cart = cartService
                        .getCartByUserId(userId);
                if(cart.isEmpty()) {
                    throw new NotFoundException("Unable to find cart");
                } else {
                    cartList = new ArrayList<>();
                    cartList.add(cart.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented for cart");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read cart");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cartList);
        return cartList;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Cart> updateCarts(@RequestParam String env,
                               @RequestBody Cart cart,
                               @RequestAttribute BigInteger currentRequestNumber,
                               ServletRequest request) throws Exception {
        Connection connection = null;
        List<Cart> cartList;
        try {
            request.setAttribute("requestBody", cart);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            cart = cartService.updateCart(cart);
            cartList = new ArrayList<>();
            cartList.add(cart);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update cart");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", cartList);
        return cartList;
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCarts(@RequestParam String env,
                                     @RequestParam(required = false) Long cartId,
                                     @RequestParam(required = false) Long userId,
                                     @RequestAttribute BigInteger currentRequestNumber,
                                     ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (cartId != null) {
                cartService.deleteCart(cartId);
            } else if (userId != null) {
                cartService.deleteCartByUserId(
                        userId);
            } else {
                throw new NotImplementedException("This delete query is not implemented cart");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete cart");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
