package az.company.onlinelibrarysystem.controller;

import az.company.onlinelibrarysystem.dto.request.LoginRequest;
import az.company.onlinelibrarysystem.dto.request.RegisterUserRequest;
import az.company.onlinelibrarysystem.dto.response.UserResponse;
import az.company.onlinelibrarysystem.entity.User;
import az.company.onlinelibrarysystem.enums.Role;
import az.company.onlinelibrarysystem.repository.UserRepository;
import az.company.onlinelibrarysystem.service.impl.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterUserRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return authService.logout();
    }
}
