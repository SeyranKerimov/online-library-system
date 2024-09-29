package az.company.onlinelibrarysystem.service;


import az.company.onlinelibrarysystem.dto.request.ReservationRequest;
import az.company.onlinelibrarysystem.dto.response.ReservationResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    ReservationResponse addReservation(ReservationRequest request);
    ReservationResponse updateReservation(Long id, ReservationRequest request);
    void cancelReservation(Long id);
    ReservationResponse getReservationById(Long id);
    List<ReservationResponse> getUserReservations(Long userId);
    List<ReservationResponse> getBookReservations(Long bookId);
    boolean checkBookAvailability(Long bookId, LocalDate startDate, LocalDate endDate);
}

