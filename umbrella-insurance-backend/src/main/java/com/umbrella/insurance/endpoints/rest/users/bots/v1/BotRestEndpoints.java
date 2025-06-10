package com.umbrella.insurance.endpoints.rest.users.bots.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.users.bots.v1.db.BotPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.users.bots.v1.db.jpa.BotService;
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
@RequestMapping(BotPrivilege.PATH)
public class BotRestEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(BotRestEndpoints.class);

    @Autowired
    private BotService botService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Bot createBot(
            @RequestParam String env,
            @RequestBody Bot bot,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Bot botResponse;
        try {
            request.setAttribute("requestBody", bot);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            botResponse = botService.saveBot(bot);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create bot");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", botResponse);
        return botResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Bot> readBots(
            @RequestParam String env,
            @RequestParam(required = false) Long botId,
            @RequestParam(required = false) String botName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Bot> botList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (botId != null) {
                Optional<Bot> bot = botService.getBotById(botId);
                if (bot.isEmpty()) {
                    throw new NotFoundException("Unable to read bot");
                } else {
                    botList = new ArrayList<>();
                    botList.add(bot.get());
                }
            } else if (botName != null) {
                Optional<Bot> bot = botService.getBotByBotName(botName);
                if (bot.isEmpty()) {
                    throw new NotFoundException("Unable to read bot");
                } else {
                    botList = new ArrayList<>();
                    botList.add(bot.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented bot ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read bot ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", botList);
        return botList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Bot> updateBots(
            @RequestParam String env,
            @RequestBody Bot bot,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Bot> botList;
        try {
            request.setAttribute("requestBody", bot);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            bot= botService.updateBot(bot);
            botList = new ArrayList<>();
            botList.add(bot);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update bot ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", botList);
        return botList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteBots(
            @RequestParam String env,
            @RequestParam(required = false) Long botId,
            @RequestParam(required = false) String botName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(botId != null) {
                botService.deleteBot(botId);
            } else if (botName != null) {
                botService.deleteBotByBotName(botName);
            } else {
                throw new NotImplementedException("This delete query is not implemented bot ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete bot ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
