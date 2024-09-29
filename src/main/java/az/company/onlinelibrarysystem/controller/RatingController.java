package az.company.onlinelibrarysystem.controller;

import az.company.onlinelibrarysystem.dto.request.RatingRequest;
import az.company.onlinelibrarysystem.dto.response.RatingResponse;
import az.company.onlinelibrarysystem.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingResponse> addRating(@RequestBody RatingRequest request) {
        return ResponseEntity.ok(ratingService.addRating(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingResponse> updateRating(@PathVariable Long id, @RequestBody RatingRequest request) {
        return ResponseEntity.ok(ratingService.updateRating(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long id) {
        return ResponseEntity.ok(ratingService.getRatingById(id));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<RatingResponse>> getAllRatingsForBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(ratingService.getAllRatingsForBook(bookId));
    }

    @GetMapping("/book/{bookId}/average")
    public ResponseEntity<Double> getAverageRatingForBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(ratingService.getAverageRatingForBook(bookId));
    }

    @GetMapping("/user/{userId}/book/{bookId}")
    public ResponseEntity<RatingResponse> getUserRatingForBook(@PathVariable Long userId, @PathVariable Long bookId) {
        return ResponseEntity.ok(ratingService.getUserRatingForBook(userId, bookId));
    }
}
