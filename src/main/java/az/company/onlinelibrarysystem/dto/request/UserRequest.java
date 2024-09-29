package az.company.onlinelibrarysystem.dto.request;

import az.company.onlinelibrarysystem.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private Role role;
}
