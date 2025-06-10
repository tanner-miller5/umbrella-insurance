package com.umbrella.insurance.endpoints.rest.rewards.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.rewards.v1.db.RewardPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.rewards.v1.db.jpa.RewardService;
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
@RequestMapping(RewardPrivilege.PATH)
public class RewardRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(RewardRestEndpoints.class);

    @Autowired
    private RewardService rewardService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Reward createReward(
            @RequestParam String env,
            @RequestBody Reward reward,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Reward rewardResponse;
        try {
            request.setAttribute("requestBody", reward);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            rewardResponse = rewardService.saveReward(reward);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create reward ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", rewardResponse);
        return rewardResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Reward> readRewards(
            @RequestParam String env,
            @RequestParam(required = false) Long rewardId,
            @RequestParam(required = false) String rewardName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Reward> rewardList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (rewardId != null) {
                Optional<Reward> reward = rewardService.getRewardById(rewardId);
                if (reward.isEmpty()) {
                    throw new NotFoundException("Unable to read reward ");
                } else {
                    rewardList = new ArrayList<>();
                    rewardList.add(reward.get());
                }
            } else if (rewardName != null) {
                Optional<Reward> reward = rewardService
                        .getRewardByName(rewardName);
                if (reward.isEmpty()) {
                    throw new NotFoundException("Unable to read reward ");
                } else {
                    rewardList = new ArrayList<>();
                    rewardList.add(reward.get());
                }
            } else {
                throw new NotImplementedException("This read query is not implemented reward ");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read reward ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", rewardList);
        return rewardList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Reward> updateRewards(
            @RequestParam String env,
            @RequestBody Reward reward,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Reward> rewardList;
        try {
            request.setAttribute("requestBody", reward);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            reward = rewardService.updateReward(
                    reward);
            rewardList = new ArrayList<>();
            rewardList.add(reward);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update reward ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", rewardList);
        return rewardList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRewards(
            @RequestParam String env,
            @RequestParam(required = false) Long rewardId,
            @RequestParam(required = false) String rewardName,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(rewardId != null) {
                rewardService.deleteReward(
                        rewardId);
            } else if (rewardName != null) {
                rewardService
                        .deleteRewardByRewardName(
                                rewardName);
            } else {
                throw new NotImplementedException("This delete query is not implemented reward ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete reward ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
