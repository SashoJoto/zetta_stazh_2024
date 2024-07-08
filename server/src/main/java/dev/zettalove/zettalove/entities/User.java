package dev.zettalove.zettalove.entities;

import dev.zettalove.zettalove.enums.Gender;
import dev.zettalove.zettalove.enums.GenderPreference;
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
            name = "date_of_birth"
    )
    private Date dateOfBirth;

    @Column(
            name = "description"
    )
    private String description;

    @Column(
            name = "address"
    )
    private String address;

    @Column(
            name = "phone_number"
    )
    private String phoneNumber;

    @Column(
            name = "desired_min_age"
    )
    private Integer desiredMinAge;

    @Column(
            name = "desired_max_age"
    )
    private Integer desiredMaxAge;

    @Column(
            name = "gender"
    )
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(
            name = "desired_gender"
    )
    @Enumerated(EnumType.STRING)
    private GenderPreference desiredGender;

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
            name = "user_interests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id"))
    private Set<Interest> interests;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<UserImage> images;

    @PrePersist
    private void defaultValues() {
        profileStatus = UserStatus.ACCOUNT_NOT_COMPLETE;
    }

}
