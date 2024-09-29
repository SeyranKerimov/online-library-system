package az.company.onlinelibrarysystem.service.impl;


import az.company.onlinelibrarysystem.dto.request.NotificationRequest;
import az.company.onlinelibrarysystem.dto.response.NotificationResponse;
import az.company.onlinelibrarysystem.entity.Notification;
import az.company.onlinelibrarysystem.entity.User;
import az.company.onlinelibrarysystem.repository.NotificationRepository;
import az.company.onlinelibrarysystem.repository.UserRepository;
import az.company.onlinelibrarysystem.service.EmailService;
import az.company.onlinelibrarysystem.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    private final EmailService emailService;

    @Override
    public void addNotification(NotificationRequest notificationRequest) {
        User user = userRepository.findById(notificationRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(notificationRequest.getMessage());
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);

        // Send email notification
        sendNotificationEmail(notificationRequest.getUserId(), notificationRequest.getMessage());
    }

    @Override
    public List<NotificationResponse> getNotificationsByUser(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void removeNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public void sendNotificationEmail(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Send email
        emailService.sendEmail(user.getEmail(), "Notification", message);
    }

    private NotificationResponse mapToResponse(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setMessage(notification.getMessage());
        response.setCreatedAt(notification.getCreatedAt());
        return response;
    }
}

