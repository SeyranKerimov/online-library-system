package az.company.onlinelibrarysystem.repository;

import az.company.onlinelibrarysystem.enums.Role;
import az.company.onlinelibrarysystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndStatus(Long id, Integer status);

    Optional<User> findByUsernameAndStatus(String usernam, Integer status);

    List<User> findByRoleAndStatus(Role role, Integer status);

    Optional<User> findByEmailAndStatus(String email,Integer status);

    List<User> findAllByStatus(Integer status);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
