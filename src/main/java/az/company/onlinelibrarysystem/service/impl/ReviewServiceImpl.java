package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.dto.request.ReviewRequest;
import az.company.onlinelibrarysystem.dto.response.ReviewResponse;
import az.company.onlinelibrarysystem.entity.Book;
import az.company.onlinelibrarysystem.entity.Review;
import az.company.onlinelibrarysystem.entity.User;
import az.company.onlinelibrarysystem.exception.CustomException;
import az.company.onlinelibrarysystem.repository.BookRepository;
import az.company.onlinelibrarysystem.repository.ReviewRepository;
import az.company.onlinelibrarysystem.repository.UserRepository;
import az.company.onlinelibrarysystem.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public ReviewResponse addReview(ReviewRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException("User not found"));
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new CustomException("Book not found"));

        Review review = new Review();
        review.setContent(request.getContent());
        review.setUser(user);
        review.setBook(book);

        reviewRepository.save(review);
        return mapToResponse(review);
    }

    @Override
    public ReviewResponse updateReview(Long id, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException("Review not found"));
        review.setContent(request.getContent());

        reviewRepository.save(review);
        return mapToResponse(review);
    }

    @Override
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new CustomException("Review not found");
        }
        reviewRepository.deleteById(id);
    }

    @Override
    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException("Review not found"));
        return mapToResponse(review);
    }

    @Override
    public List<ReviewResponse> getAllReviewsForBook(Long bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);
        return reviews.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse getUserReviewForBook(Long userId, Long bookId) {
        Review review = reviewRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new CustomException("Review not found"));
        return mapToResponse(review);
    }

    private ReviewResponse mapToResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setContent(review.getContent());
        response.setUsername(review.getUser().getUsername());
        response.setBookTitle(review.getBook().getTitle());
        return response;
    }
}

