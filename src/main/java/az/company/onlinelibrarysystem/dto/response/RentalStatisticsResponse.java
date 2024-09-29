package az.company.onlinelibrarysystem.dto.response;


import lombok.Data;

@Data
public class RentalStatisticsResponse {
    private Long totalRentals;
    private Long activeRentals;
    private Long returnedBooks;
}
