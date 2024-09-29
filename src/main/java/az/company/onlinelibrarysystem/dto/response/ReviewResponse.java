package az.company.onlinelibrarysystem.dto.response;

import lombok.Data;

@Data
public class ReviewResponse {
    private Long id;
    private String content;
    private String username;  // Username of the user who wrote the review
    private String bookTitle;  // Title of the book being reviewed
}

