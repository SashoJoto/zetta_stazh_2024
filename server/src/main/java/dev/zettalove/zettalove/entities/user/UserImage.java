package dev.zettalove.zettalove.entities.user;

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
@Entity(name = "UserImage")
@Table(name = "user_image")
public class UserImage {
    @Id
    @SequenceGenerator(
            name = "user_image_sequence",
            sequenceName = "user_image_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_image_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "image_url",
            nullable = false
    )
    private String imageUrl;

    @Column(
            name = "order_index",
            nullable = false
    )
    private Integer orderIndex;
}
