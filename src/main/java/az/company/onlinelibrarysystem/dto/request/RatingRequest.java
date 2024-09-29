package az.company.onlinelibrarysystem.dto.request;


import lombok.Data;

@Data
public class RatingRequest {
    private int score;
    private Long userId;  // The ID of the user giving the rating
    private Long bookId;  // The ID of the book being rated
}
