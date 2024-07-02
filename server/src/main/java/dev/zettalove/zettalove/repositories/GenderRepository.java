package dev.zettalove.zettalove.repositories;

import dev.zettalove.zettalove.entities.gender.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Long> {
}
