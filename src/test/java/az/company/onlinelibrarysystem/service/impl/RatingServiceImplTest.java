package az.company.onlinelibrarysystem.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
import az.company.onlinelibrarysystem.service.impl.RatingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Test
    void testAddRating_Success() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;
        int score = 5;
        RatingRequest request = new RatingRequest();
        request.setUserId(userId);
        request.setBookId(bookId);
        request.setScore(score);

        User user = new User();
        user.setUsername("User1");

        Book book = new Book();
        book.setTitle("Book1");

        Rating rating = new Rating();
        rating.setScore(score);
        rating.setUser(user);
        rating.setBook(book);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        // Act
        RatingResponse response = ratingService.addRating(request);

        // Assert
        assertEquals(score, response.getScore());
        assertEquals("User1", response.getUsername());
        assertEquals("Book1", response.getBookTitle());

        ArgumentCaptor<Rating> ratingCaptor = ArgumentCaptor.forClass(Rating.class);
        verify(ratingRepository, times(1)).save(ratingCaptor.capture());
        Rating savedRating = ratingCaptor.getValue();
        assertEquals(score, savedRating.getScore());
        assertEquals(user, savedRating.getUser());
        assertEquals(book, savedRating.getBook());
    }

    @Test
    void testAddRating_UserNotFound() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;
        RatingRequest request = new RatingRequest();
        request.setUserId(userId);
        request.setBookId(bookId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                ratingService.addRating(request)
        );
        assertEquals("User not found", thrown.getMessage());
    }

    @Test
    void testAddRating_BookNotFound() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;
        RatingRequest request = new RatingRequest();
        request.setUserId(userId);
        request.setBookId(bookId);

        User user = new User();
        user.setUsername("User1");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                ratingService.addRating(request)
        );
        assertEquals("Book not found", thrown.getMessage());
    }


    @Test
    void testUpdateRating_RatingNotFound() {
        // Arrange
        Long ratingId = 1L;
        RatingRequest request = new RatingRequest();
        request.setScore(4);

        when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                ratingService.updateRating(ratingId, request)
        );
        assertEquals("Rating not found", thrown.getMessage());
    }

    @Test
    void testDeleteRating_Success() {
        // Arrange
        Long ratingId = 1L;
        when(ratingRepository.existsById(ratingId)).thenReturn(true);

        // Act
        ratingService.deleteRating(ratingId);

        // Assert
        verify(ratingRepository, times(1)).deleteById(ratingId);
    }

    @Test
    void testDeleteRating_RatingNotFound() {
        // Arrange
        Long ratingId = 1L;
        when(ratingRepository.existsById(ratingId)).thenReturn(false);

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                ratingService.deleteRating(ratingId)
        );
        assertEquals("Rating not found", thrown.getMessage());
    }

    @Test
    void testGetRatingById_Success() {
        // Arrange
        Long ratingId = 1L;
        Rating rating = new Rating();
        rating.setId(ratingId);
        rating.setScore(5);
        rating.setUser(new User());
        rating.setBook(new Book());

        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));

        // Act
        RatingResponse response = ratingService.getRatingById(ratingId);

        // Assert
        assertEquals(ratingId, response.getId());
        assertEquals(rating.getScore(), response.getScore());
        assertEquals(rating.getUser().getUsername(), response.getUsername());
        assertEquals(rating.getBook().getTitle(), response.getBookTitle());
    }

    @Test
    void testGetRatingById_RatingNotFound() {
        // Arrange
        Long ratingId = 1L;
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                ratingService.getRatingById(ratingId)
        );
        assertEquals("Rating not found", thrown.getMessage());
    }


    @Test
    void testGetAverageRatingForBook_Success() {
        // Arrange
        Long bookId = 1L;
        Rating rating1 = new Rating();
        rating1.setScore(4);
        Rating rating2 = new Rating();
        rating2.setScore(5);

        when(ratingRepository.findByBookId(bookId)).thenReturn(List.of(rating1, rating2));

        // Act
        Double averageRating = ratingService.getAverageRatingForBook(bookId);

        // Assert
        assertEquals(4.5, averageRating);
    }

    @Test
    void testGetUserRatingForBook_Success() {
        // Arrange
        Long userId = 1L;
        Long bookId = 2L;
        Rating rating = new Rating();
        rating.setScore(5);
        User user = new User();
        user.setUsername("User1");
        Book book = new Book();
        book.setTitle("Book1");

        rating.setUser(user);
        rating.setBook(book);

        when(ratingRepository.findByUserIdAndBookId(userId, bookId)).thenReturn(Optional.of(rating));

        // Act
        RatingResponse response = ratingService.getUserRatingForBook(userId, bookId);

        // Assert
        assertEquals(rating.getScore(), response.getScore());
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(book.getTitle(), response.getBookTitle());
    }
}
