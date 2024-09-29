package az.company.onlinelibrarysystem.dto.response;


import lombok.Data;
import java.util.List;

@Data
public class AuthorResponse {
    private Long id;
    private String name;
    private String biography;
    private List<String> bookTitles; // List of book titles associated with the author
}

