package az.company.onlinelibrarysystem.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import az.company.onlinelibrarysystem.dto.request.NotificationRequest;
import az.company.onlinelibrarysystem.dto.response.NotificationResponse;
import az.company.onlinelibrarysystem.entity.Notification;
import az.company.onlinelibrarysystem.entity.User;
import az.company.onlinelibrarysystem.repository.NotificationRepository;
import az.company.onlinelibrarysystem.repository.UserRepository;
import az.company.onlinelibrarysystem.service.EmailService;
import az.company.onlinelibrarysystem.service.NotificationService;
import az.company.onlinelibrarysystem.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Test
    void testAddNotification_Success() {
        // Arrange
        Long userId = 1L;
        String message = "Notification message";
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setUserId(userId);
        notificationRequest.setMessage(message);

        User user = new User();
        user.setEmail("user@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        notificationService.addNotification(notificationRequest);

        // Assert
        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository, times(1)).save(notificationCaptor.capture());
        Notification savedNotification = notificationCaptor.getValue();
        assertNotNull(savedNotification.getCreatedAt());
        assertEquals(user, savedNotification.getUser());
        assertEquals(message, savedNotification.getMessage());

        verify(emailService, times(1)).sendEmail(user.getEmail(), "Notification", message);
    }

    @Test
    void testAddNotification_UserNotFound() {
        // Arrange
        Long userId = 1L;
        String message = "Notification message";
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setUserId(userId);
        notificationRequest.setMessage(message);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                notificationService.addNotification(notificationRequest)
        );
        assertEquals("User not found", thrown.getMessage());
    }

    @Test
    void testGetNotificationsByUser_Success() {
        // Arrange
        Long userId = 1L;
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setMessage("Message 1");
        notification1.setCreatedAt(LocalDateTime.now());

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setMessage("Message 2");
        notification2.setCreatedAt(LocalDateTime.now());

        when(notificationRepository.findByUserId(userId)).thenReturn(List.of(notification1, notification2));

        // Act
        List<NotificationResponse> responses = notificationService.getNotificationsByUser(userId);

        // Assert
        assertEquals(2, responses.size());
        assertEquals(notification1.getId(), responses.get(0).getId());
        assertEquals(notification1.getMessage(), responses.get(0).getMessage());
        assertEquals(notification2.getId(), responses.get(1).getId());
        assertEquals(notification2.getMessage(), responses.get(1).getMessage());
    }

    @Test
    void testRemoveNotification_Success() {
        // Arrange
        Long notificationId = 1L;

        // Act
        notificationService.removeNotification(notificationId);

        // Assert
        verify(notificationRepository, times(1)).deleteById(notificationId);
    }

    @Test
    void testSendNotificationEmail_Success() {
        // Arrange
        Long userId = 1L;
        String message = "Notification message";
        User user = new User();
        user.setEmail("user@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        notificationService.sendNotificationEmail(userId, message);

        // Assert
        verify(emailService, times(1)).sendEmail(user.getEmail(), "Notification", message);
    }

    @Test
    void testSendNotificationEmail_UserNotFound() {
        // Arrange
        Long userId = 1L;
        String message = "Notification message";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                notificationService.sendNotificationEmail(userId, message)
        );
        assertEquals("User not found", thrown.getMessage());
    }
}
