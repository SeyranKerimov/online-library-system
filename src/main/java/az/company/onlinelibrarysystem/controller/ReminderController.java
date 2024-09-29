package az.company.onlinelibrarysystem.controller;

import az.company.onlinelibrarysystem.dto.request.ReminderRequest;
import az.company.onlinelibrarysystem.dto.response.ReminderResponse;
import az.company.onlinelibrarysystem.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping
    public ResponseEntity<String> addReminder(@RequestBody ReminderRequest reminderRequest) {
        reminderService.addReminder(reminderRequest);
        return new ResponseEntity<>("Reminder added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{reminderId}")
    public ResponseEntity<String> removeReminder(@PathVariable Long reminderId) {
        reminderService.removeReminder(reminderId);
        return new ResponseEntity<>("Reminder removed successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReminderResponse>> getRemindersByUser(@PathVariable Long userId) {
        List<ReminderResponse> reminders = reminderService.getRemindersByUser(userId);
        return new ResponseEntity<>(reminders, HttpStatus.OK);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<ReminderResponse>> getUpcomingReminders(@RequestParam("date") LocalDate date) {
        List<ReminderResponse> reminders = reminderService.getUpcomingReminders(date);
        return new ResponseEntity<>(reminders, HttpStatus.OK);
    }

    @PostMapping("/notify/{userId}")
    public ResponseEntity<String> sendReminderNotification(@PathVariable Long userId, @RequestParam("message") String message) {
        reminderService.sendReminderNotification(userId, message);
        return new ResponseEntity<>("Reminder notification sent", HttpStatus.OK);
    }
}
