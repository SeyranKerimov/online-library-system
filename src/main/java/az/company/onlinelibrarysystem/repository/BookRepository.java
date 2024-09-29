package az.company.onlinelibrarysystem.repository;

import az.company.onlinelibrarysystem.entity.Author;
import az.company.onlinelibrarysystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findByTitleContainingOrIsbnContaining(String title, String isbn);

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE "
            + "(?1 IS NULL OR b.category = ?1) AND "
            + "(?2 IS NULL OR a.name = ?2) AND "
            + "(?3 IS NULL OR b.yearPublished = ?3)")
    List<Book> filterBooks(String category, String author, Integer yearPublished);

    // Find books by a specific author
    List<Book> findBooksByAuthorsContains(Author author);
}
