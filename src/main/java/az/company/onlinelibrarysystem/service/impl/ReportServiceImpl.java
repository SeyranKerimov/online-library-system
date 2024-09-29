package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.dto.response.BookRentalHistoryResponse;
import az.company.onlinelibrarysystem.dto.response.MostReadBooksResponse;
import az.company.onlinelibrarysystem.dto.response.RentalStatisticsResponse;
import az.company.onlinelibrarysystem.dto.response.UserActivityReportResponse;
import az.company.onlinelibrarysystem.entity.Rental;
import az.company.onlinelibrarysystem.repository.RentalRepository;
import az.company.onlinelibrarysystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final RentalRepository rentalRepository;

    @Override
    public RentalStatisticsResponse generateRentalStatistics() {
        RentalStatisticsResponse response = new RentalStatisticsResponse();
        response.setTotalRentals(rentalRepository.count());
        response.setActiveRentals(rentalRepository.countByActive(true));
        response.setReturnedBooks(rentalRepository.countByActive(false));
        return response;
    }

    @Override
    public List<MostReadBooksResponse> getMostReadBooks() {
        List<Object[]> mostReadBooksData = rentalRepository.findMostReadBooks();
        return mostReadBooksData.stream().map(result -> {
            MostReadBooksResponse response = new MostReadBooksResponse();
            response.setBookId((Long) result[0]);
            response.setBookTitle((String) result[1]);
            response.setRentalCount((Long) result[2]);
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserActivityReportResponse> generateUserActivityReport() {
        List<Object[]> userActivityData = rentalRepository.findUserActivityReport();
        return userActivityData.stream().map(result -> {
            UserActivityReportResponse response = new UserActivityReportResponse();
            response.setUserId((Long) result[0]);
            response.setUsername((String) result[1]);
            response.setTotalRentals((Long) result[2]);
            response.setActiveRentals((Long) result[3]);
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public List<BookRentalHistoryResponse> getBookRentalHistory(Long bookId) {
        List<Rental> rentals = rentalRepository.findByBookId(bookId);
        return rentals.stream().map(rental -> {
            BookRentalHistoryResponse response = new BookRentalHistoryResponse();
            response.setRentalId(rental.getId());
            response.setUserId(rental.getUser().getId());
            response.setUsername(rental.getUser().getUsername());
            response.setRentalDate(rental.getRentalDate());
            response.setReturnDate(rental.getReturnDate());
            return response;
        }).collect(Collectors.toList());
    }
}

