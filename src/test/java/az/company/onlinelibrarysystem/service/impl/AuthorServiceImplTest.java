package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.dto.request.AuthorRequest;
import az.company.onlinelibrarysystem.dto.response.AuthorResponse;
import az.company.onlinelibrarysystem.entity.Author;
import az.company.onlinelibrarysystem.entity.Book;
import az.company.onlinelibrarysystem.exception.CustomException;
import az.company.onlinelibrarysystem.repository.AuthorRepository;
import az.company.onlinelibrarysystem.repository.BookRepository;
import az.company.onlinelibrarysystem.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    public AuthorServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRemoveBookFromAuthor() {
        // Arrange
        Author author = new Author();
        author.setId(1L);
        Book book = new Book();
        book.setId(1L);
        author.setBooks(new ArrayList<>(Arrays.asList(book)));

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        // Act
        authorService.removeBookFromAuthor(1L, 1L);

        // Assert
        assertTrue(author.getBooks().isEmpty());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void testRemoveBookFromAuthor_ThrowsException_WhenAuthorNotFound() {
        // Arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                authorService.removeBookFromAuthor(1L, 1L)
        );
        assertEquals("Author not found", thrown.getMessage());
    }

    @Test
    void testRemoveBookFromAuthor_ThrowsException_WhenBookNotFound() {
        // Arrange
        Author author = new Author();
        author.setId(1L);
        author.setBooks(new ArrayList<>());

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                authorService.removeBookFromAuthor(1L, 1L)
        );
        assertEquals("Book not found", thrown.getMessage());
    }

    @Test
    void testAddAuthor() {
        // Arrange
        AuthorRequest request = new AuthorRequest();
        request.setName("Author Name");
        request.setBiography("Author Biography");
        request.setBookIds(Arrays.asList(1L, 2L));

        Book book1 = new Book();
        book1.setId(1L);
        Book book2 = new Book();
        book2.setId(2L);

        when(bookRepository.findAllById(any())).thenReturn(Arrays.asList(book1, book2));

        Author author = new Author();
        author.setId(1L);
        author.setName("Author Name");
        author.setBiography("Author Biography");
        author.setBooks(Arrays.asList(book1, book2));

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        // Act
        AuthorResponse response = authorService.addAuthor(request);

        // Assert
        assertNotNull(response);
        assertEquals("Author Name", response.getName());
        assertEquals("Author Biography", response.getBiography());
        assertEquals(2, response.getBookTitles().size());
    }

    @Test
    void testUpdateAuthor() {
        // Arrange
        AuthorRequest request = new AuthorRequest();
        request.setName("Updated Name");
        request.setBiography("Updated Biography");
        request.setBookIds(Arrays.asList(1L, 2L));

        Author author = new Author();
        author.setId(1L);
        author.setName("Old Name");
        author.setBiography("Old Biography");
        author.setBooks(new ArrayList<>());

        Book book1 = new Book();
        book1.setId(1L);
        Book book2 = new Book();
        book2.setId(2L);

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(bookRepository.findAllById(any())).thenReturn(Arrays.asList(book1, book2));
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        // Act
        AuthorResponse response = authorService.updateAuthor(1L, request);

        // Assert
        assertNotNull(response);
        assertEquals("Updated Name", response.getName());
        assertEquals("Updated Biography", response.getBiography());
        assertEquals(2, response.getBookTitles().size());
    }

    @Test
    void testUpdateAuthor_ThrowsException_WhenAuthorNotFound() {
        // Arrange
        AuthorRequest request = new AuthorRequest();
        request.setName("Updated Name");
        request.setBiography("Updated Biography");
        request.setBookIds(Arrays.asList(1L, 2L));

        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                authorService.updateAuthor(1L, request)
        );
        assertEquals("Author not found", thrown.getMessage());
    }

    @Test
    void testDeleteAuthor() {
        // Arrange
        when(authorRepository.existsById(anyLong())).thenReturn(true);

        // Act
        authorService.deleteAuthor(1L);

        // Assert
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAuthor_ThrowsException_WhenAuthorNotFound() {
        // Arrange
        when(authorRepository.existsById(anyLong())).thenReturn(false);

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                authorService.deleteAuthor(1L)
        );
        assertEquals("Author not found", thrown.getMessage());
    }

    @Test
    void testGetAuthorById_ThrowsException_WhenAuthorNotFound() {
        // Arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                authorService.getAuthorById(1L)
        );
        assertEquals("Author not found", thrown.getMessage());
    }

    @Test
    void testGetAuthorByName_ThrowsException_WhenAuthorNotFound() {
        // Arrange
        when(authorRepository.findByName(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () ->
                authorService.getAuthorByName("Nonexistent Name")
        );
        assertEquals("Author not found", thrown.getMessage());
    }
}

