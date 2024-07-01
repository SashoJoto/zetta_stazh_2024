package dev.zettalove.zettalove.entities.user;

import dev.zettalove.zettalove.entities.gender.Gender;
import dev.zettalove.zettalove.entities.preference.Preference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(
        name = "user_",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email"),
        }
)
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;

    @Column(
            name = "date_of_birth",
            nullable = false
    )
    private Date dateOfBirth;

    @Column(
            name = "email",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @Column(
            name = "description",
            nullable = false
    )
    private String description;

    @Column(
            name = "address",
            nullable = false
    )
    private String address;

    @Column(
            name = "phone_number",
            nullable = false
    )
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(
            name = "gender_id",
            nullable = false
    )
    private Gender gender;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "liked_user_id"))
    private List<User> likedUsers;

    @ManyToMany
    @JoinTable(
            name = "user_matches",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "matched_user_id"))
    private List<User>  matchedUsers;

    @ManyToMany
    @JoinTable(
            name = "user_preferences",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "preference_id"))
    private List<Preference> preferences;

}
