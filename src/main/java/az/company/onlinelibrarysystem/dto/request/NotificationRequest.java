package az.company.onlinelibrarysystem.dto.request;

import lombok.Data;

@Data
public class NotificationRequest {
    private Long userId;
    private String message;
}


