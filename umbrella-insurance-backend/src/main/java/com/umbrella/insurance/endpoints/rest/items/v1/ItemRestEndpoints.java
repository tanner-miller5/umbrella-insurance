package com.umbrella.insurance.endpoints.rest.items.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.Item;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.items.v1.db.ItemPrivilege;
import com.umbrella.insurance.core.models.items.v1.db.jpa.ItemService;
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
@RequestMapping(ItemPrivilege.PATH)
public class ItemRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(ItemRestEndpoints.class);

    @Autowired
    private ItemService itemService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Item createItem(
            @RequestParam String env,
            @RequestBody Item item,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Item itemResponse;
        try {
            request.setAttribute("requestBody", item);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            itemResponse = itemService.saveItem(item);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create item");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", itemResponse);
        return itemResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Item> readItems(
            @RequestParam String env,
            @RequestParam(required = false) Long itemId,
            @RequestParam(required = false) String itemName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Item> itemList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (itemId != null) {
                Optional<Item> item = itemService.getItemById(itemId);
                if(item.isEmpty()) {
                    throw new NotFoundException("Item not found");
                } else {
                    itemList = new ArrayList<>();
                    itemList.add(item.get());
                }
            } else if (itemName != null) {
                Optional<Item> item = itemService.getItemByItemName(itemName);
                if(item.isEmpty()) {
                    throw new NotFoundException("Item not found");
                } else {
                    itemList = new ArrayList<>();
                    itemList.add(item.get());
                }
            } else {
                itemList = itemService.getItems();
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read item");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", itemList);
        return itemList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Item> updateItems(
            @RequestParam String env,
            @RequestBody Item item,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Item> itemList;
        try {
            request.setAttribute("requestBody", item);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            item = itemService.updateItem(
                    item);
            itemList = new ArrayList<>();
            itemList.add(item);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update item");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", itemList);
        return itemList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteItems(@RequestParam String env,
                    @RequestParam(required = false) Long itemId,
                    @RequestParam(required = false) String itemName,
                    @RequestAttribute BigInteger currentRequestNumber,
                    ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(itemId != null) {
                itemService.deleteItem(itemId);
            } else if (itemName != null) {
                itemService.deleteItemByItemName(itemName);
            } else {
                throw new NotImplementedException("This delete query is not implemented item");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete item");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
