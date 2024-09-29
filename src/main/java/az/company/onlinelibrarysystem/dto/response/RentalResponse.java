package az.company.onlinelibrarysystem.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RentalResponse {
    private Long rentalId;
    private Long userId;
    private Long bookId;
    private String bookTitle;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean active;
}
