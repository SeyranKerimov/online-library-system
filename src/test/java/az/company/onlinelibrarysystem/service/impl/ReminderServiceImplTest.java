package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.dto.request.ReminderRequest;
import az.company.onlinelibrarysystem.entity.Book;
import az.company.onlinelibrarysystem.entity.Reminder;
import az.company.onlinelibrarysystem.entity.User;
import az.company.onlinelibrarysystem.exception.CustomException;
import az.company.onlinelibrarysystem.repository.BookRepository;
import az.company.onlinelibrarysystem.repository.ReminderRepository;
import az.company.onlinelibrarysystem.repository.UserRepository;
import az.company.onlinelibrarysystem.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReminderServiceImplTest {

    @Mock
    private ReminderRepository reminderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ReminderServiceImpl reminderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addReminder_Success() {
        // Arrange
        ReminderRequest request = new ReminderRequest();
        request.setUserId(1L);
        request.setBookId(2L);
        request.setReminderDate(LocalDate.of(2024, 9, 30));

        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        Book book = new Book();
        book.setId(2L);
        book.setTitle("Test Book");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(book));

        // Act
        reminderService.addReminder(request);

        // Assert
        verify(reminderRepository, times(1)).save(any(Reminder.class));
    }

    @Test
    void addReminder_UserNotFound() {
        // Arrange
        ReminderRequest request = new ReminderRequest();
        request.setUserId(1L);
        request.setBookId(2L);
        request.setReminderDate(LocalDate.of(2024, 9, 30));

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            reminderService.addReminder(request);
        });

        assertEquals("User not found", exception.getMessage());
        verify(reminderRepository, never()).save(any(Reminder.class));
    }

    @Test
    void addReminder_BookNotFound() {
        // Arrange
        ReminderRequest request = new ReminderRequest();
        request.setUserId(1L);
        request.setBookId(2L);
        request.setReminderDate(LocalDate.of(2024, 9, 30));

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            reminderService.addReminder(request);
        });

        assertEquals("Book not found", exception.getMessage());
        verify(reminderRepository, never()).save(any(Reminder.class));
    }
}
