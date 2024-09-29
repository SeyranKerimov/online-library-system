package az.company.onlinelibrarysystem.controller;

import az.company.onlinelibrarysystem.dto.request.UserRequest;
import az.company.onlinelibrarysystem.dto.response.UserResponse;
import az.company.onlinelibrarysystem.enums.Role;
import az.company.onlinelibrarysystem.service.EmailService;
import az.company.onlinelibrarysystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JavaMailSender javaMailSender;
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.addUser(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.updateUser(userRequest, id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponses = userService.getAllUsers();
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        UserResponse userResponse = userService.getUserByUsername(username);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable Role role) {
        List<UserResponse> userResponses = userService.getUsersByRole(role);
        return ResponseEntity.ok(userResponses);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }
}