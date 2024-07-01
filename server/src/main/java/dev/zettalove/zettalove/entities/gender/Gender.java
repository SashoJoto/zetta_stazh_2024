package dev.zettalove.zettalove.entities.gender;

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
@Entity(name = "Gender")
@Table(
        name = "gender",
        uniqueConstraints = {
                @UniqueConstraint(name = "gender_name_unique", columnNames = "name"),
        }
)
public class Gender {
    @Id
    @SequenceGenerator(
            name = "gender_sequence",
            sequenceName = "gender_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "gender_sequence"
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

    @Column(
            name = "description",
            nullable = false
    )
    private String description;

    @OneToMany(mappedBy = "gender", cascade = CascadeType.ALL)
    private List<User> users;
}
