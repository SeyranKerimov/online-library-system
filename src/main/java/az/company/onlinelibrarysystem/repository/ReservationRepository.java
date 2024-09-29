package az.company.onlinelibrarysystem.repository;

import az.company.onlinelibrarysystem.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByBookId(Long bookId);

    List<Reservation> findByBookIdAndActive(Long bookId, boolean b);
}
