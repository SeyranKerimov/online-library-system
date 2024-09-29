package az.company.onlinelibrarysystem.dto.response;

import lombok.Data;

@Data
public class RatingResponse {
    private Long id;
    private int score;
    private String username;  // Username of the user who gave the rating
    private String bookTitle;  // Title of the book being rated
}
