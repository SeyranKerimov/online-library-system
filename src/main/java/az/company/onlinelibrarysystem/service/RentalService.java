package az.company.onlinelibrarysystem.service;


import az.company.onlinelibrarysystem.dto.request.RentalRequest;
import az.company.onlinelibrarysystem.dto.response.RentalResponse;

import java.util.List;

public interface RentalService {
    RentalResponse rentBook(RentalRequest rentalRequest);
    List<RentalResponse> getActiveRentalsByUser(Long userId);
    void returnBook(Long rentalId);
    List<RentalResponse> getOverdueRentals();
    void sendOverdueNotifications();
}
