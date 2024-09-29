package az.company.onlinelibrarysystem.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class BookResponse {
    private Long id;
    private String title;
    private String isbn;
    private int yearPublished;
    private String category;
    private String language;
    private List<String> authorNames;  // List of author names

    // Getters and setters
}
