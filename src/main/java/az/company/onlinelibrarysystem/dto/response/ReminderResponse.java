package az.company.onlinelibrarysystem.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReminderResponse {
    private Long id;
    private LocalDate reminderDate;
    private Long userId;
    private Long bookId;
    private String bookTitle; // Add if you need to return more details about the book
}
