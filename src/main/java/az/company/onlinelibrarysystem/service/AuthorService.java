package az.company.onlinelibrarysystem.service;

import az.company.onlinelibrarysystem.dto.request.AuthorRequest;
import az.company.onlinelibrarysystem.dto.response.AuthorResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse addAuthor(AuthorRequest request);
    AuthorResponse updateAuthor(Long id, AuthorRequest request);
    void deleteAuthor(Long id);
    AuthorResponse getAuthorById(Long id);
    List<AuthorResponse> getAllAuthors();
    AuthorResponse getAuthorByName(String name);
    void removeBookFromAuthor(Long authorId, Long bookId);

}
