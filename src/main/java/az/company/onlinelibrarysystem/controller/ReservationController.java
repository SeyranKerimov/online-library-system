package az.company.onlinelibrarysystem.controller;

import az.company.onlinelibrarysystem.dto.request.ReservationRequest;
import az.company.onlinelibrarysystem.dto.response.ReservationResponse;
import az.company.onlinelibrarysystem.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.addReservation(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable Long id, @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.updateReservation(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponse>> getUserReservations(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getUserReservations(userId));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReservationResponse>> getBookReservations(@PathVariable Long bookId) {
        return ResponseEntity.ok(reservationService.getBookReservations(bookId));
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkBookAvailability(@RequestParam Long bookId,
                                                         @RequestParam LocalDate startDate,
                                                         @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(reservationService.checkBookAvailability(bookId, startDate, endDate));
    }
}

