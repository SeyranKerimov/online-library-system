package az.company.onlinelibrarysystem.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class LoginRequest {
    @NotEmpty(message = "username can not be empty")
    @NotNull(message = "username can not be empty")
    private String username;

    @NotEmpty(message = "password can not be empty")
    @NotNull(message = "password can not be empty")
    private String password;
}
