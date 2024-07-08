package dev.zettalove.zettalove.repositories;

import dev.zettalove.zettalove.entities.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
}
