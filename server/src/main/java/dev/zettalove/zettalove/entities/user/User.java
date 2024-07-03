package dev.zettalove.zettalove.entities.user;

import dev.zettalove.zettalove.entities.gender.Gender;
import dev.zettalove.zettalove.entities.preference.Preference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(
            name = "min_age",
            nullable = false
    )
    private Integer minAge = 18;  // Default value

    @Column(
            name = "max_age",
            nullable = false
    )
    private Integer maxAge = 99;  // Default value

    @ManyToOne
    @JoinColumn(
            name = "gender_id",
            nullable = false
    )
    private Gender gender;

    @ManyToOne
    @JoinColumn(
            name = "desired_gender_id",
            nullable = false
    )
    private Gender desiredGender;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "liked_user_id"))
    private Set<User> likedUsers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_matches",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "matched_user_id"))
    private Set<User> matchedUsers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_preferences",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "preference_id"))
    private List<Preference> preferences;

    @ManyToMany
    @JoinTable(
            name = "recommended_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recommended_user_id"))
    private Set<User> recommended = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "swiped_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "swiped_user_id"))
    private Set<User> swiped = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<UserImage> images;

    public void addImage(UserImage image) {
        images.add(image);
    }

    public void removeImage(UserImage image) {
        images.remove(image);
    }
}
