package az.company.onlinelibrarysystem.service;

import az.company.onlinelibrarysystem.dto.request.ReminderRequest;
import az.company.onlinelibrarysystem.dto.response.ReminderResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReminderService {
    void addReminder(ReminderRequest reminderRequest);
    void removeReminder(Long reminderId);
    List<ReminderResponse> getRemindersByUser(Long userId);
    List<ReminderResponse> getUpcomingReminders(LocalDate date);
    void sendReminderNotification(Long userId, String message);
}

