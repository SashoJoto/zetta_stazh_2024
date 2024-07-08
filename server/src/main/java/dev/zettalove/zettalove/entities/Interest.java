package dev.zettalove.zettalove.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Interest")
@Table(
        name = "interest",
        uniqueConstraints = {
                @UniqueConstraint(name = "interest_name_unique", columnNames = "name"),
        }
)
public class Interest {
    @Id
    @SequenceGenerator(
            name = "interest_sequence",
            sequenceName = "interest_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "interest_sequence"
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
