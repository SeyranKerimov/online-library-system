package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.exception.CustomException;
import az.company.onlinelibrarysystem.repository.AuthorRepository;
import az.company.onlinelibrarysystem.repository.BookRepository;
import az.company.onlinelibrarysystem.dto.request.BookRequest;
import az.company.onlinelibrarysystem.dto.response.BookResponse;
import az.company.onlinelibrarysystem.entity.Author;
import az.company.onlinelibrarysystem.entity.Book;
import az.company.onlinelibrarysystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    @Override
    public BookResponse addBook(BookRequest bookRequest) {
        // Create a new book entity from BookRequest
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setIsbn(bookRequest.getIsbn());
        book.setYearPublished(bookRequest.getYearPublished());
        book.setLanguage(bookRequest.getLanguage());
        book.setCategory(bookRequest.getCategory());

        // Fetch authors by IDs and set them to the book
        List<Author> authors = authorRepository.findAllById(bookRequest.getAuthorIds());
        book.setAuthors(authors);

        // Update the books list in each author
        for (Author author : authors) {
            author.getBooks().add(book);
        }

        // Save the book
        Book savedBook = bookRepository.save(book);

        // Save authors if needed
        authorRepository.saveAll(authors);

        return convertToResponse(savedBook);
    }


    @Override
    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        // Fetch existing book by ID
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new CustomException("Book not found with id " + id));

        // Update book details
        existingBook.setTitle(bookRequest.getTitle());
        existingBook.setIsbn(bookRequest.getIsbn());
        existingBook.setYearPublished(bookRequest.getYearPublished());
        existingBook.setLanguage(bookRequest.getLanguage());
        existingBook.setCategory(bookRequest.getCategory());

        // Fetch authors by IDs and set them to the book
        List<Author> authors = authorRepository.findAllById(bookRequest.getAuthorIds());
        existingBook.setAuthors(authors);

        // Save the updated book and return the response
        Book updatedBook = bookRepository.save(existingBook);
        return convertToResponse(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new CustomException("Book not found with id " + id));
        bookRepository.delete(book);
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new CustomException("Book not found with id " + id));
        return convertToResponse(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingOrIsbnContaining(keyword, keyword).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> filterBooks(String category, String author, Integer yearPublished) {
        return bookRepository.filterBooks(category, author, yearPublished).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new CustomException("Author not found with id " + authorId));
        return bookRepository.findBooksByAuthorsContains(author).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    // Helper method to convert Book entity to BookResponse
    private BookResponse convertToResponse(Book book) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setIsbn(book.getIsbn());
        bookResponse.setLanguage(book.getLanguage());
        bookResponse.setCategory(book.getCategory());
        bookResponse.setYearPublished(book.getYearPublished());
        bookResponse.setAuthorNames(book.getAuthors().stream()
                .map(Author::getName).collect(Collectors.toList()));  // Get author names
        return bookResponse;
    }
}
