package az.company.onlinelibrarysystem.service;

import az.company.onlinelibrarysystem.dto.request.BookRequest;
import az.company.onlinelibrarysystem.dto.response.BookResponse;

import java.util.List;

public interface BookService {

    // Add a new book
    BookResponse addBook(BookRequest bookRequestDTO);

    // Update an existing book
    BookResponse updateBook(Long id, BookRequest bookRequestDTO);

    // Delete a book by ID
    void deleteBook(Long id);

    // Get a book by its ID
    BookResponse getBookById(Long id);

    // Get a list of all books
    List<BookResponse> getAllBooks();

    // Search books based on criteria (title, author, year, etc.)
    List<BookResponse> searchBooks(String keyword);

    // Filter books by category, author, publication year, etc.
    List<BookResponse> filterBooks(String category, String author, Integer yearPublished);

    // Get the list of books by a specific author
    List<BookResponse> getBooksByAuthor(Long authorId);

    long count();
}


