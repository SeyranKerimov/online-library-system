package az.company.onlinelibrarysystem.service;

import az.company.onlinelibrarysystem.dto.response.BookRentalHistoryResponse;
import az.company.onlinelibrarysystem.dto.response.MostReadBooksResponse;
import az.company.onlinelibrarysystem.dto.response.RentalStatisticsResponse;
import az.company.onlinelibrarysystem.dto.response.UserActivityReportResponse;

import java.util.List;

public interface ReportService {
    RentalStatisticsResponse generateRentalStatistics();
    List<MostReadBooksResponse> getMostReadBooks();
    List<UserActivityReportResponse> generateUserActivityReport();
    List<BookRentalHistoryResponse> getBookRentalHistory(Long bookId);
}
