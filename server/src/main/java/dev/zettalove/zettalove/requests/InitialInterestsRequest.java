package dev.zettalove.zettalove.requests;

import dev.zettalove.zettalove.enums.Gender;
import dev.zettalove.zettalove.enums.GenderPreference;
import lombok.Data;

import java.util.Date;

@Data
public class InitialInterestsRequest {
    private Date dateOfBirth;
    private String description;
    private String address;
    private String phoneNumber;
    private Integer desiredMinAge;
    private Integer desiredMaxAge;
    private Gender gender;
    private GenderPreference desiredGender;
    private String[] interests;
}
