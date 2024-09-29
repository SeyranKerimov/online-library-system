package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.dto.request.ReservationRequest;
import az.company.onlinelibrarysystem.dto.response.ReservationResponse;
import az.company.onlinelibrarysystem.entity.Book;
import az.company.onlinelibrarysystem.entity.Reservation;
import az.company.onlinelibrarysystem.entity.User;
import az.company.onlinelibrarysystem.repository.BookRepository;
import az.company.onlinelibrarysystem.repository.ReservationRepository;
import az.company.onlinelibrarysystem.repository.UserRepository;
import az.company.onlinelibrarysystem.service.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public ReservationResponse addReservation(ReservationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        Reservation reservation = new Reservation();
        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());
        reservation.setActive(true);
        reservation.setUser(user);
        reservation.setBook(book);

        reservationRepository.save(reservation);
        return mapToResponse(reservation);
    }

    @Override
    public ReservationResponse updateReservation(Long id, ReservationRequest request) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());

        reservationRepository.save(reservation);
        return mapToResponse(reservation);
    }

    @Override
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));

        reservation.setActive(false);
        reservationRepository.save(reservation);
    }

    @Override
    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
        return mapToResponse(reservation);
    }

    @Override
    public List<ReservationResponse> getUserReservations(Long userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        return reservations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> getBookReservations(Long bookId) {
        List<Reservation> reservations = reservationRepository.findByBookId(bookId);
        return reservations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkBookAvailability(Long bookId, LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = reservationRepository.findByBookIdAndActive(bookId, true);
        return reservations.stream().noneMatch(reservation ->
                (startDate.isBefore(reservation.getEndDate()) && endDate.isAfter(reservation.getStartDate()))
        );
    }

    private ReservationResponse mapToResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setStartDate(reservation.getStartDate());
        response.setEndDate(reservation.getEndDate());
        response.setActive(reservation.isActive());
        response.setUsername(reservation.getUser().getUsername());
        response.setBookTitle(reservation.getBook().getTitle());
        return response;
    }
}

