package az.company.onlinelibrarysystem.service;

import az.company.onlinelibrarysystem.dto.request.ReviewRequest;
import az.company.onlinelibrarysystem.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {
    ReviewResponse addReview(ReviewRequest request);
    ReviewResponse updateReview(Long id, ReviewRequest request);
    void deleteReview(Long id);
    ReviewResponse getReviewById(Long id);
    List<ReviewResponse> getAllReviewsForBook(Long bookId);
    ReviewResponse getUserReviewForBook(Long userId, Long bookId);
}

