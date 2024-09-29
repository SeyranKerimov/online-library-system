package az.company.onlinelibrarysystem.controller;

import az.company.onlinelibrarysystem.dto.request.ReviewRequest;
import az.company.onlinelibrarysystem.dto.response.ReviewResponse;
import az.company.onlinelibrarysystem.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(@RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.addReview(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id, @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.updateReview(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsForBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(reviewService.getAllReviewsForBook(bookId));
    }

    @GetMapping("/user/{userId}/book/{bookId}")
    public ResponseEntity<ReviewResponse> getUserReviewForBook(@PathVariable Long userId, @PathVariable Long bookId) {
        return ResponseEntity.ok(reviewService.getUserReviewForBook(userId, bookId));
    }
}

