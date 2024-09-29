package az.company.onlinelibrarysystem.repository;

import az.company.onlinelibrarysystem.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUserId(Long userId);
}
