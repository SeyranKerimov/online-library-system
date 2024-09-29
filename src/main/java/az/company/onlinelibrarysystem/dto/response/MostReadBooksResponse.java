package az.company.onlinelibrarysystem.dto.response;


import lombok.Data;

@Data
public class MostReadBooksResponse {
    private Long bookId;
    private String bookTitle;
    private Long rentalCount;
}

