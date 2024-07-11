package dev.zettalove.zettalove.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.zettalove.zettalove.entities.User;
import dev.zettalove.zettalove.enums.Gender;
import dev.zettalove.zettalove.enums.GenderPreference;
import dev.zettalove.zettalove.security.KeycloakProvider;
import lombok.Data;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private UUID id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String description;
    private String address;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private Gender gender;
    @JsonProperty("desired_gender")
    private GenderPreference desiredGender;
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;
    private Integer age;
    private Set<InterestDto> interests;

    public UserDto(User user, UserRepresentation keycloakUser) {
        this.id = user.getId();
        this.description = user.getDescription();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.desiredGender = user.getDesiredGender();
        this.dateOfBirth = user.getDateOfBirth();
        this.age = LocalDate.now().getYear() - user.getDateOfBirth().getYear();
        this.interests = user.getInterests().stream().map(InterestDto::new).collect(Collectors.toSet());

        this.firstName = keycloakUser.getFirstName();
        this.lastName = keycloakUser.getLastName();
    }
}