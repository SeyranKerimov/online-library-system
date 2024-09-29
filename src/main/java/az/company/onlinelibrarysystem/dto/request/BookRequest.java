package az.company.onlinelibrarysystem.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BookRequest {
    private String title;
    private String isbn;
    private int yearPublished;
    private String category;
    private String language;
    private List<Long> authorIds;  // List of author IDs
}

