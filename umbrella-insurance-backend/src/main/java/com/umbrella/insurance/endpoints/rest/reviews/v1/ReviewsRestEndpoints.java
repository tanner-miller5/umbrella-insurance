package com.umbrella.insurance.endpoints.rest.reviews.v1;

import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.ConflictException;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.reviews.v1.db.ReviewPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.reviews.v1.db.jpa.ReviewService;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.postgresql.util.PSQLState.UNIQUE_VIOLATION;

@Controller
@RequestMapping(ReviewPrivilege.PATH)
public class ReviewsRestEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(ReviewsRestEndpoints.class);

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Review createReview(
            @RequestParam String env,
            @RequestBody Review review,
            @RequestAttribute BigInteger currentRequestNumber,
            @RequestHeader String session,
            ServletRequest request) throws Exception {
        Connection connection = null;
        Review reviewResponse;
        try {
            request.setAttribute("requestBody", review);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            Optional<Session> session1 = sessionService.getSessionBySessionCode(session);
            Optional<User> user = userService.getUserByUsername(review.getUser().getUsername());
            if (session1.isEmpty()) {
                throw new Exception("Unable to create review because session does not exist");
            } else if (user.isEmpty()) {
                throw new Exception("Unable to create review because user does not exist");
            } else {
                review.setSession(session1.get());
                review.setUser(user.get());
                reviewResponse = reviewService.saveReview(review);
            }

        } catch (SQLException e) {
            logger.error("Logging Request Number: {}, message: {}, sqlState: {}", currentRequestNumber,
                    e.getMessage(), e.getSQLState());
            //https://www.postgresql.org/docs/current/errcodes-appendix.html
            if (e.getSQLState().equals(UNIQUE_VIOLATION)) {
                throw new ConflictException("review  unique violation");
            } else {
                throw new Exception("Unable to create review ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to create review ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", reviewResponse);
        return reviewResponse;
    }
    @CrossOrigin
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Review> readReviews(
            @RequestParam String env,
            @RequestParam(required = false) Long reviewId,
            @RequestParam(required = false) String frontendAppVersion,
            @RequestParam(required = false) String backendAppVersion,
            @RequestAttribute BigInteger currentRequestNumber,
            @RequestParam(required = false) Long userId,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Review> reviewList;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(userId != null && frontendAppVersion != null && backendAppVersion != null) {
                Optional<Review> review = reviewService.getReviewByUserIdAndFrontendAppVersionAndBackendAppVersion(
                        userId, frontendAppVersion, backendAppVersion);
                if (review.isEmpty()) {
                    reviewList = new ArrayList<>();
                    //throw new NotFoundException("Unable to read review ");
                } else {
                    reviewList = new ArrayList<>();
                    reviewList.add(review.get());
                }
            } else if (frontendAppVersion != null && backendAppVersion != null) {
                reviewList = reviewService.getReviewByFrontendAppVersionAndBackendAppVersion(
                        frontendAppVersion, backendAppVersion);
            } else if (reviewId != null) {
                Optional<Review> review = reviewService.getReviewByReviewId(reviewId);
                if (review.isEmpty()) {
                    throw new NotFoundException("Unable to read review ");
                } else {
                    reviewList = new ArrayList<>();
                    reviewList.add(review.get());
                }
            } else {
                reviewList = reviewService.getReviews();
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read review ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", reviewList);
        return reviewList;
    }
    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<Review> updateReviews(
            @RequestParam String env,
            @RequestBody Review review,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        List<Review> reviewList;
        try {
            request.setAttribute("requestBody", review);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            reviewService.updateReview(
                    review);
            reviewList = new ArrayList<>();
            reviewList.add(review);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update review ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", reviewList);
        return reviewList;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteReviews(@RequestParam String env,
                             @RequestParam(required = false) Long reviewId,
                             @RequestParam(required = false) String frontendAppVersion,
                             @RequestParam(required = false) String backendAppVersion,
                             @RequestAttribute BigInteger currentRequestNumber,
                             @RequestParam(required = false) Long userId,
                             ServletRequest request) throws Exception {
        Connection connection = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if(reviewId != null) {
                reviewService.deleteReview(reviewId);
            } else if (userId != null && frontendAppVersion != null && backendAppVersion != null) {
                reviewService.deleteReviewByUserIdAndFrontendAppVersionAndBackendAppVersion(
                        userId, frontendAppVersion, backendAppVersion);
            } else {
                throw new NotImplementedException("This delete query is not implemented review ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete review ");
        } finally {
            Database.closeConnection(connection);
        }
    }
}
