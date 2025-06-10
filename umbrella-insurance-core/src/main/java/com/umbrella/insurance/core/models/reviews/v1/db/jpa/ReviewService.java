package com.umbrella.insurance.core.models.reviews.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Review saveReview(Review review);
    List<Review> getReviews();
    Review updateReview(Review review);
    void deleteReview(Long reviewId);
    Optional<Review> getReviewByUserIdAndFrontendAppVersionAndBackendAppVersion(
            Long userId, String frontendAppVersion, String backendAppVersion);
    Optional<Review> getReviewByReviewId(Long reviewId);
    Optional<Review> getReviewBySessionId(Long sessionId);
    void deleteReviewByUserIdAndFrontendAppVersionAndBackendAppVersion(Long userId,
                                                                       String frontendAppVersion, String backendAppVersion);
    List<Review> getReviewByFrontendAppVersionAndBackendAppVersion(
            String frontendAppVersion, String backendAppVersion);
    void deleteReviewByUserId(Long userId);
}
