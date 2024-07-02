package dev.zettalove.zettalove.entities.preference;


import dev.zettalove.zettalove.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Preference")
@Table(
        name = "preference",
        uniqueConstraints = {
                @UniqueConstraint(name = "preference_name_unique", columnNames = "name"),
        }
)
public class Preference {
    @Id
    @SequenceGenerator(
            name = "preference_sequence",
            sequenceName = "preference_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "preference_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            unique = true
    )
    private String name;
}
