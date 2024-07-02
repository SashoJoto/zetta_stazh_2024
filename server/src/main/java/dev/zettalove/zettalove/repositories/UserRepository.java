package dev.zettalove.zettalove.repositories;

import dev.zettalove.zettalove.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
