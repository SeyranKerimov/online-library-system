package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.dto.request.BookRequest;
import az.company.onlinelibrarysystem.dto.response.BookResponse;
import az.company.onlinelibrarysystem.entity.Author;
import az.company.onlinelibrarysystem.entity.Book;
import az.company.onlinelibrarysystem.exception.CustomException;
import az.company.onlinelibrarysystem.repository.AuthorRepository;
import az.company.onlinelibrarysystem.repository.BookRepository;
import az.company.onlinelibrarysystem.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateBook() {
        // Arrange
        BookRequest request = new BookRequest();
        request.setTitle("Updated Title");
        request.setIsbn("0987654321");
        request.setYearPublished(2024);
        request.setLanguage("Spanish");
        request.setCategory("Non-Fiction");
        request.setAuthorIds(Arrays.asList(1L, 3L));

        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Old Title");
        existingBook.setIsbn("1234567890");
        existingBook.setYearPublished(2023);
        existingBook.setLanguage("English");
        existingBook.setCategory("Fiction");

        Author author1 = new Author();
        author1.setId(1L);
        Author author3 = new Author();
        author3.setId(3L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(existingBook));
        when(authorRepository.findAllById(anyList())).thenReturn(Arrays.asList(author1, author3));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        // Act
        BookResponse response = bookService.updateBook(1L, request);

        // Assert
        assertNotNull(response);
        assertEquals("Updated Title", response.getTitle());
        assertEquals("0987654321", response.getIsbn());
        assertEquals(2024, response.getYearPublished());
        assertEquals("Spanish", response.getLanguage());
        assertEquals("Non-Fiction", response.getCategory());
        assertEquals(2, response.getAuthorNames().size());
    }

    @Test
    void testUpdateBook_ThrowsException_WhenBookNotFound() {
        // Arrange
        BookRequest request = new BookRequest();
        request.setTitle("Updated Title");

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                bookService.updateBook(1L, request)
        );
        assertEquals("Book not found with id 1", thrown.getMessage());
    }

    @Test
    void testDeleteBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        // Act
        bookService.deleteBook(1L);

        // Assert
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    void testDeleteBook_ThrowsException_WhenBookNotFound() {
        // Arrange
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                bookService.deleteBook(1L)
        );
        assertEquals("Book not found with id 1", thrown.getMessage());
    }

    @Test
    void testGetBookById_ThrowsException_WhenBookNotFound() {
        // Arrange
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                bookService.getBookById(1L)
        );
        assertEquals("Book not found with id 1", thrown.getMessage());
    }

    @Test
    void testGetBooksByAuthor_ThrowsException_WhenAuthorNotFound() {
        // Arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                bookService.getBooksByAuthor(1L)
        );
        assertEquals("Author not found with id 1", thrown.getMessage());
    }
}
