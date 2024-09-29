package az.company.onlinelibrarysystem.repository;

import az.company.onlinelibrarysystem.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Long> {

    Optional<Author> findByName(String name);
}
