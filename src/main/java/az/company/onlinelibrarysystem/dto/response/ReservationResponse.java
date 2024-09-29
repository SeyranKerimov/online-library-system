package az.company.onlinelibrarysystem.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
    private String username;  // Username of the user who made the reservation
    private String bookTitle;  // Title of the reserved book
}

