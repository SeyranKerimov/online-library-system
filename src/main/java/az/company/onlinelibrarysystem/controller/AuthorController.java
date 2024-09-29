package az.company.onlinelibrarysystem.controller;

import az.company.onlinelibrarysystem.dto.request.AuthorRequest;
import az.company.onlinelibrarysystem.dto.response.AuthorResponse;
import az.company.onlinelibrarysystem.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponse> addAuthor(@RequestBody AuthorRequest request) {
        return ResponseEntity.ok(authorService.addAuthor(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable Long id, @RequestBody AuthorRequest request) {
        return ResponseEntity.ok(authorService.updateAuthor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<Void> removeBookFromAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        authorService.removeBookFromAuthor(authorId, bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/search")
    public ResponseEntity<AuthorResponse> getAuthorByName(@RequestParam String name) {
        return ResponseEntity.ok(authorService.getAuthorByName(name));
    }
}

