package dev.zettalove.zettalove.entities.user;

import dev.zettalove.zettalove.entities.gender.Gender;
import dev.zettalove.zettalove.entities.preference.Preference;
import dev.zettalove.zettalove.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "user_")
public class User {
    @Id
    @Column(
            name = "id",
            nullable = false
    )
    private UUID Id;

    @Column(
            name = "profile_status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private UserStatus profileStatus;

    @Column(
            name = "date_of_birth",
            nullable = false
    )
    private Date dateOfBirth;

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

    @PrePersist
    private void defaultValues() {
        profileStatus = UserStatus.PREFERENCES_MISSING;
    }

}
