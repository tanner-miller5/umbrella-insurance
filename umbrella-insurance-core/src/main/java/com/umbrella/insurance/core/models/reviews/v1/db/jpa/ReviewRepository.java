package com.umbrella.insurance.core.models.reviews.v1.db.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umbrella.insurance.core.models.entities.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> getReviewByUserIdAndFrontendAppVersionAndBackendAppVersion(
            Long userId, String frontendAppVersion, String backendAppVersion);
    void deleteReviewByUserIdAndFrontendAppVersionAndBackendAppVersion(Long userId,
                                                                       String frontendAppVersion, String backendAppVersion);
    Optional<Review> getReviewBySessionId(Long sessionId);
    List<Review> getReviewByFrontendAppVersionAndBackendAppVersion(String frontendAppVersion, String backendAppVersion);
    void deleteReviewByUserId(Long userId);

}
