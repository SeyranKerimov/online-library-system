package az.company.onlinelibrarysystem.repository;

import az.company.onlinelibrarysystem.entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder,Long> {
    List<Reminder> findByUserId(Long userId);

    List<Reminder> findByReminderDateAfter(LocalDate date);
}
