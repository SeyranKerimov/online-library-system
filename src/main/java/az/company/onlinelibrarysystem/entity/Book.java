package az.company.onlinelibrarysystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "books", schema = "library")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;
    private Integer yearPublished;
    private String language;
    private String category;

    @ManyToMany(mappedBy = "books",cascade = CascadeType.ALL)
    private List<Author> authors;

    @OneToMany(mappedBy = "book")
    private List<Rating> ratings;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews;

    @OneToMany(mappedBy = "book")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "book")
    private List<Rental> rentals;
}

