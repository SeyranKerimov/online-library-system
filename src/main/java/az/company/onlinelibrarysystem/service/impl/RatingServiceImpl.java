package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.dto.request.RatingRequest;
import az.company.onlinelibrarysystem.dto.response.RatingResponse;
import az.company.onlinelibrarysystem.entity.Book;
import az.company.onlinelibrarysystem.entity.Rating;
import az.company.onlinelibrarysystem.entity.User;
import az.company.onlinelibrarysystem.exception.CustomException;
import az.company.onlinelibrarysystem.repository.BookRepository;
import az.company.onlinelibrarysystem.repository.RatingRepository;
import az.company.onlinelibrarysystem.repository.UserRepository;
import az.company.onlinelibrarysystem.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public RatingResponse addRating(RatingRequest request) {
        User user = getUser(request);
        Book book = getBook(request);

        Rating rating = new Rating();
        rating.setScore(request.getScore());
        rating.setUser(user);
        rating.setBook(book);

        ratingRepository.save(rating);
        return mapToResponse(rating);
    }

    private Book getBook(RatingRequest request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new CustomException("Book not found"));
        return book;
    }

    private User getUser(RatingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException("User not found"));
        return user;
    }

    @Override
    public RatingResponse updateRating(Long id, RatingRequest request) {
        Rating rating = getRating(ratingRepository.findById(id));
        rating.setScore(request.getScore());

        ratingRepository.save(rating);
        return mapToResponse(rating);
    }

    private Rating getRating(Optional<Rating> ratingRepository) {
        Rating rating = ratingRepository
                .orElseThrow(() -> new CustomException("Rating not found"));
        return rating;
    }

    @Override
    public void deleteRating(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new CustomException("Rating not found");
        }
        ratingRepository.deleteById(id);
    }

    @Override
    public RatingResponse getRatingById(Long id) {
        Rating rating = getRating(ratingRepository.findById(id));
        return mapToResponse(rating);
    }

    @Override
    public List<RatingResponse> getAllRatingsForBook(Long bookId) {
        List<Rating> ratings = ratingRepository.findByBookId(bookId);
        return ratings.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageRatingForBook(Long bookId) {
        List<Rating> ratings = ratingRepository.findByBookId(bookId);
        return ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);
    }

    @Override
    public RatingResponse getUserRatingForBook(Long userId, Long bookId) {
        Rating rating = getRating(ratingRepository.findByUserIdAndBookId(userId, bookId));
        return mapToResponse(rating);
    }

    private RatingResponse mapToResponse(Rating rating) {
        RatingResponse response = new RatingResponse();
        response.setId(rating.getId());
        response.setScore(rating.getScore());
        response.setUsername(rating.getUser().getUsername());
        response.setBookTitle(rating.getBook().getTitle());
        return response;
    }
}

