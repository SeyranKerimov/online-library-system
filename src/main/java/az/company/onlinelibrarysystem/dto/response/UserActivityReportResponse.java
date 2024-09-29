package az.company.onlinelibrarysystem.dto.response;

import lombok.Data;

@Data
public class UserActivityReportResponse {
    private Long userId;
    private String username;
    private Long totalRentals;
    private Long activeRentals;
}
