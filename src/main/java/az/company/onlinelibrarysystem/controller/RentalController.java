package az.company.onlinelibrarysystem.controller;

import az.company.onlinelibrarysystem.dto.request.RentalRequest;
import az.company.onlinelibrarysystem.dto.response.RentalResponse;
import az.company.onlinelibrarysystem.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping("/rent")
    public RentalResponse rentBook(@RequestBody RentalRequest rentalRequest) {
        return rentalService.rentBook(rentalRequest);
    }

    @GetMapping("/user/{userId}")
    public List<RentalResponse> getActiveRentalsByUser(@PathVariable Long userId) {
        return rentalService.getActiveRentalsByUser(userId);
    }

    @PostMapping("/return/{rentalId}")
    public ResponseEntity<?> returnBook(@PathVariable Long rentalId) {
        rentalService.returnBook(rentalId);
        return ResponseEntity.ok("Book returned successfully");
    }

    @GetMapping("/overdue")
    public List<RentalResponse> getOverdueRentals() {
        return rentalService.getOverdueRentals();
    }

    @PostMapping("/send-overdue-notifications")
    public ResponseEntity<?> sendOverdueNotifications() {
        rentalService.sendOverdueNotifications();
        return ResponseEntity.ok("Overdue notifications sent.");
    }
}
