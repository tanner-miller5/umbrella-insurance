package com.umbrella.insurance.core.models.reviews.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review updateReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public Optional<Review> getReviewByUserIdAndFrontendAppVersionAndBackendAppVersion(Long userId, String frontendAppVersion, String backendAppVersion) {
        return reviewRepository.getReviewByUserIdAndFrontendAppVersionAndBackendAppVersion(
                userId, frontendAppVersion, backendAppVersion
        );
    }

    @Override
    public Optional<Review> getReviewByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    @Override
    public Optional<Review> getReviewBySessionId(Long sessionId) {
        return reviewRepository.getReviewBySessionId(sessionId);
    }

    @Override
    public void deleteReviewByUserIdAndFrontendAppVersionAndBackendAppVersion(
            Long userId, String frontendAppVersion, String backendAppVersion) {
        reviewRepository.deleteReviewByUserIdAndFrontendAppVersionAndBackendAppVersion(userId,
                frontendAppVersion, backendAppVersion);
    }

    @Override
    public List<Review> getReviewByFrontendAppVersionAndBackendAppVersion(
            String frontendAppVersion, String backendAppVersion) {
        return reviewRepository.getReviewByFrontendAppVersionAndBackendAppVersion(
                frontendAppVersion, backendAppVersion
        );
    }

    @Override
    public void deleteReviewByUserId(Long userId) {
        reviewRepository.deleteReviewByUserId(userId);
    }
}
