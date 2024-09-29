package az.company.onlinelibrarysystem.controller;

import az.company.onlinelibrarysystem.dto.request.BookRequest;
import az.company.onlinelibrarysystem.dto.response.BookResponse;
import az.company.onlinelibrarysystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // Add a new book
    @PostMapping
    public ResponseEntity<BookResponse> addBook(@RequestBody BookRequest bookRequest) {
        BookResponse bookResponse = bookService.addBook(bookRequest);
        return ResponseEntity.ok(bookResponse);
    }

    // Update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        BookResponse updatedBook = bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok(updatedBook);
    }

    // Delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Get a book by its ID
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        BookResponse bookResponse = bookService.getBookById(id);
        return ResponseEntity.ok(bookResponse);
    }

    // Get all books
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // Search books by title or ISBN
    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooks(@RequestParam String keyword) {
        List<BookResponse> books = bookService.searchBooks(keyword);
        return ResponseEntity.ok(books);
    }

    // Filter books by category, author, and yearPublished
    @GetMapping("/filter")
    public ResponseEntity<List<BookResponse>> filterBooks(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer yearPublished) {
        List<BookResponse> books = bookService.filterBooks(category, author, yearPublished);
        return ResponseEntity.ok(books);
    }

    // Get books by a specific author
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@PathVariable Long authorId) {
        List<BookResponse> books = bookService.getBooksByAuthor(authorId);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getBooksCount() {
        Long booksCount = bookService.count();
        return ResponseEntity.ok(booksCount);
    }
}

