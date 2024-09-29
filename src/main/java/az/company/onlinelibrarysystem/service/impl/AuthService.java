package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.dto.request.LoginRequest;
import az.company.onlinelibrarysystem.dto.request.RegisterUserRequest;
import az.company.onlinelibrarysystem.dto.response.UserResponse;
import az.company.onlinelibrarysystem.entity.User;
import az.company.onlinelibrarysystem.enums.Role;
import az.company.onlinelibrarysystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<String> registerUser(RegisterUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.SUPERADMIN);  // Assign default role

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");


    }

    public ResponseEntity<UserResponse> login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        UserResponse response = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(response);

    }

    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully");
    }
}
