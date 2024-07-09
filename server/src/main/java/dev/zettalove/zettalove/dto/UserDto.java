package dev.zettalove.zettalove.dto;

import dev.zettalove.zettalove.entities.Interest;
import dev.zettalove.zettalove.entities.User;
import dev.zettalove.zettalove.enums.Gender;
import dev.zettalove.zettalove.enums.GenderPreference;
import lombok.Data;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private UUID id;
    private String description;
    private String address;
    private String phoneNumber;
    private Gender gender;
    private GenderPreference desiredGender;
    private Set<InterestDto> interests;

    public UserDto(User user){
        this.id = user.getId();
        this.description = user.getDescription();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.desiredGender = user.getDesiredGender();
        this.interests = user.getInterests().stream().map(InterestDto::new).collect(Collectors.toSet());
    }

}