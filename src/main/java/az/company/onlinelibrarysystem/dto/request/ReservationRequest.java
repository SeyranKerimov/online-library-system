package az.company.onlinelibrarysystem.dto.request;


import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long userId;  // The ID of the user making the reservation
    private Long bookId;  // The ID of the book being reserved
}

