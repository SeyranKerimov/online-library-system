package az.company.onlinelibrarysystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "reviews",schema = "library")
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}

