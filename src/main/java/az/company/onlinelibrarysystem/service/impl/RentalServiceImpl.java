package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.dto.request.RentalRequest;
import az.company.onlinelibrarysystem.dto.response.RentalResponse;
import az.company.onlinelibrarysystem.entity.Book;
import az.company.onlinelibrarysystem.entity.Rental;
import az.company.onlinelibrarysystem.entity.User;
import az.company.onlinelibrarysystem.repository.BookRepository;
import az.company.onlinelibrarysystem.repository.RentalRepository;
import az.company.onlinelibrarysystem.repository.UserRepository;
import az.company.onlinelibrarysystem.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RentalResponse rentBook(RentalRequest rentalRequest) {
        User user = userRepository.findById(rentalRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(rentalRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentalDate(rentalRequest.getRentalDate());
        rental.setReturnDate(rentalRequest.getReturnDate());
        rental.setActive(true);

        Rental savedRental = rentalRepository.save(rental);

        return mapToRentalResponse(savedRental);
    }

    @Override
    public List<RentalResponse> getActiveRentalsByUser(Long userId) {
        return rentalRepository.findByUserIdAndActiveTrue(userId)
                .stream()
                .map(this::mapToRentalResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void returnBook(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));
        rental.setActive(false);
        rentalRepository.save(rental);
    }

    @Override
    public List<RentalResponse> getOverdueRentals() {
        return rentalRepository.findByReturnDateBeforeAndActiveTrue(LocalDate.now())
                .stream()
                .map(this::mapToRentalResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void sendOverdueNotifications() {
        List<RentalResponse> overdueRentals = getOverdueRentals();
        for (RentalResponse rental : overdueRentals) {
            // Notification logic (SMS, email, etc.)
            System.out.println("Reminder: Book " + rental.getBookTitle() + " is overdue.");
        }
    }

    private RentalResponse mapToRentalResponse(Rental rental) {
        RentalResponse rentalResponse = new RentalResponse();
        rentalResponse.setRentalId(rental.getId());
        rentalResponse.setUserId(rental.getUser().getId());
        rentalResponse.setBookId(rental.getBook().getId());
        rentalResponse.setBookTitle(rental.getBook().getTitle());
        rentalResponse.setRentalDate(rental.getRentalDate());
        rentalResponse.setReturnDate(rental.getReturnDate());
        rentalResponse.setActive(rental.isActive());
        return rentalResponse;
    }
}
