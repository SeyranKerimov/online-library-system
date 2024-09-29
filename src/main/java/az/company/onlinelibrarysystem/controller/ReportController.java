package az.company.onlinelibrarysystem.controller;

import az.company.onlinelibrarysystem.dto.response.BookRentalHistoryResponse;
import az.company.onlinelibrarysystem.dto.response.MostReadBooksResponse;
import az.company.onlinelibrarysystem.dto.response.RentalStatisticsResponse;
import az.company.onlinelibrarysystem.dto.response.UserActivityReportResponse;
import az.company.onlinelibrarysystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/rental-statistics")
    public ResponseEntity<RentalStatisticsResponse> getRentalStatistics() {
        RentalStatisticsResponse statistics = reportService.generateRentalStatistics();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/most-read-books")
    public ResponseEntity<List<MostReadBooksResponse>> getMostReadBooks() {
        List<MostReadBooksResponse> mostReadBooks = reportService.getMostReadBooks();
        return ResponseEntity.ok(mostReadBooks);
    }

    @GetMapping("/user-activity-report")
    public ResponseEntity<List<UserActivityReportResponse>> getUserActivityReport() {
        List<UserActivityReportResponse> userActivityReport = reportService.generateUserActivityReport();
        return ResponseEntity.ok(userActivityReport);
    }

    @GetMapping("/book-rental-history/{bookId}")
    public ResponseEntity<List<BookRentalHistoryResponse>> getBookRentalHistory(@PathVariable Long bookId) {
        List<BookRentalHistoryResponse> bookRentalHistory = reportService.getBookRentalHistory(bookId);
        return ResponseEntity.ok(bookRentalHistory);
    }
}

