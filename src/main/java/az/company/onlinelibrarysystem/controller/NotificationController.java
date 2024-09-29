package az.company.onlinelibrarysystem.controller;


import az.company.onlinelibrarysystem.dto.request.NotificationRequest;
import az.company.onlinelibrarysystem.dto.response.NotificationResponse;
import az.company.onlinelibrarysystem.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> addNotification(@RequestBody NotificationRequest notificationRequest) {
        notificationService.addNotification(notificationRequest);
        return new ResponseEntity<>("Notification added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByUser(@PathVariable Long userId) {
        List<NotificationResponse> notifications = notificationService.getNotificationsByUser(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> removeNotification(@PathVariable Long notificationId) {
        notificationService.removeNotification(notificationId);
        return new ResponseEntity<>("Notification removed successfully", HttpStatus.NO_CONTENT);
    }
}

