package dev.zettalove.zettalove.repositories;

import dev.zettalove.zettalove.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);
    Optional<List<User>> findAllByProfileStatusEquals(String profileStatus);
}
