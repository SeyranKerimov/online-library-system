package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.dto.request.ReminderRequest;
import az.company.onlinelibrarysystem.dto.response.ReminderResponse;
import az.company.onlinelibrarysystem.entity.Book;
import az.company.onlinelibrarysystem.entity.Reminder;
import az.company.onlinelibrarysystem.entity.User;
import az.company.onlinelibrarysystem.exception.CustomException;
import az.company.onlinelibrarysystem.repository.BookRepository;
import az.company.onlinelibrarysystem.repository.ReminderRepository;
import az.company.onlinelibrarysystem.repository.UserRepository;
import az.company.onlinelibrarysystem.service.EmailService;
import az.company.onlinelibrarysystem.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void addReminder(ReminderRequest reminderRequest) {
        User user = userRepository.findById(reminderRequest.getUserId())
                .orElseThrow(() -> new CustomException("User not found"));
        Book book = bookRepository.findById(reminderRequest.getBookId())
                .orElseThrow(() -> new CustomException("Book not found"));

        Reminder reminder = new Reminder();
        reminder.setUser(user);
        reminder.setBook(book);
        reminder.setReminderDate(reminderRequest.getReminderDate());

        reminderRepository.save(reminder);
    }

    @Override
    public void removeReminder(Long reminderId) {
        reminderRepository.deleteById(reminderId);
    }

    @Override
    public List<ReminderResponse> getRemindersByUser(Long userId) {
        return reminderRepository.findByUserId(userId).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public List<ReminderResponse> getUpcomingReminders(LocalDate date) {
        return reminderRepository.findByReminderDateAfter(date).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void sendReminderNotification(Long userId, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));

        // Send email
        emailService.sendEmail(user.getEmail(), "Reminder Notification", message);
    }

    private ReminderResponse mapToResponse(Reminder reminder) {
        ReminderResponse response = new ReminderResponse();
        response.setId(reminder.getId());
        response.setReminderDate(reminder.getReminderDate());
        response.setUserId(reminder.getUser().getId());
        response.setBookId(reminder.getBook().getId());
        response.setBookTitle(reminder.getBook().getTitle());
        return response;
    }
}
