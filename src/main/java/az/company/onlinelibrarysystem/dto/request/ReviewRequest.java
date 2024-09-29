package az.company.onlinelibrarysystem.dto.request;

import lombok.Data;

@Data
public class ReviewRequest {
    private String content;
    private Long userId;  // The ID of the user writing the review
    private Long bookId;  // The ID of the book being reviewed
}
