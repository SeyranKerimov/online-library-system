package az.company.onlinelibrarysystem.dto.request;


import lombok.Data;
import java.util.List;

@Data
public class AuthorRequest {
    private String name;
    private String biography;
    private List<Long> bookIds; // List of book IDs to associate with the author
}

