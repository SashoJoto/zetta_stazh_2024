package dev.zettalove.zettalove.repositories;

import dev.zettalove.zettalove.entities.preference.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
