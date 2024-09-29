package az.company.onlinelibrarysystem.service;

import az.company.onlinelibrarysystem.dto.request.NotificationRequest;
import az.company.onlinelibrarysystem.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    void addNotification(NotificationRequest notificationRequest);
    List<NotificationResponse> getNotificationsByUser(Long userId);
    void removeNotification(Long notificationId);
    void sendNotificationEmail(Long userId, String message);
}

