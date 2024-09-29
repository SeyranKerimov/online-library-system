package az.company.onlinelibrarysystem.dto.request;


import lombok.Data;

import java.time.LocalDate;

@Data
public class ReminderRequest {
    private Long userId;
    private Long bookId;
    private LocalDate reminderDate;
}
