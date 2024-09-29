package az.company.onlinelibrarysystem.service.impl;

import az.company.onlinelibrarysystem.dto.request.AuthorRequest;
import az.company.onlinelibrarysystem.dto.response.AuthorResponse;
import az.company.onlinelibrarysystem.entity.Author;
import az.company.onlinelibrarysystem.entity.Book;
import az.company.onlinelibrarysystem.exception.CustomException;
import az.company.onlinelibrarysystem.repository.AuthorRepository;
import az.company.onlinelibrarysystem.repository.BookRepository;
import az.company.onlinelibrarysystem.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public void removeBookFromAuthor(Long authorId, Long bookId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new CustomException("Author not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException("Book not found"));

        // Remove the book from the author's book list
        author.getBooks().remove(book);

        authorRepository.save(author);
    }


    @Override
    public AuthorResponse addAuthor(AuthorRequest request) {
        Author author = new Author();
        author.setName(request.getName());
        author.setBiography(request.getBiography());
        author.setBooks(bookRepository.findAllById(request.getBookIds()));

        authorRepository.save(author);
        return mapToResponse(author);
    }

    @Override
    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {
        Author author = getAuthor(authorRepository.findById(id));
        author.setName(request.getName());
        author.setBiography(request.getBiography());
        author.setBooks(bookRepository.findAllById(request.getBookIds()));

        authorRepository.save(author);
        return mapToResponse(author);
    }

    private Author getAuthor(Optional<Author> authorRepository) {
        Author author = authorRepository
                .orElseThrow(() -> new CustomException("Author not found"));
        return author;
    }

    @Override
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new CustomException("Author not found");
        }
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorResponse getAuthorById(Long id) {
        Author author = getAuthor(authorRepository.findById(id));
        return mapToResponse(author);
    }

    @Override
    public List<AuthorResponse> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorResponse getAuthorByName(String name) {
        Author author = getAuthor(authorRepository.findByName(name));
        return mapToResponse(author);
    }

    private AuthorResponse mapToResponse(Author author) {
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setName(author.getName());
        response.setBiography(author.getBiography());
        response.setBookTitles(author.getBooks().stream()
                .map(Book::getTitle)
                .collect(Collectors.toList()));
        return response;
    }
}

