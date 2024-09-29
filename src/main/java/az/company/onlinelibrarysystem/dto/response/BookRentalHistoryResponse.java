package az.company.onlinelibrarysystem.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRentalHistoryResponse {
    private Long rentalId;
    private Long userId;
    private String username;
    private LocalDate rentalDate;
    private LocalDate returnDate;
}

