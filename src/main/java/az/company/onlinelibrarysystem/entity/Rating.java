package az.company.onlinelibrarysystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "ratings",schema = "library")
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}

