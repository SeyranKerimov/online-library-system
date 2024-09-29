package az.company.onlinelibrarysystem.service;


import az.company.onlinelibrarysystem.dto.request.RatingRequest;
import az.company.onlinelibrarysystem.dto.response.RatingResponse;

import java.util.List;

public interface RatingService {
    RatingResponse addRating(RatingRequest request);
    RatingResponse updateRating(Long id, RatingRequest request);
    void deleteRating(Long id);
    RatingResponse getRatingById(Long id);
    List<RatingResponse> getAllRatingsForBook(Long bookId);
    Double getAverageRatingForBook(Long bookId);
    RatingResponse getUserRatingForBook(Long userId, Long bookId);
}

