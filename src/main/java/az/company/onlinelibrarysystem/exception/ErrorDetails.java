package az.company.onlinelibrarysystem.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDetails {
    private int statusCode;
    private String message;
    private String details;

    public ErrorDetails(int statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }

    // Getters and setters
}

