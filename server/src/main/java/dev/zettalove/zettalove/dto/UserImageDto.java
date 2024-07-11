package dev.zettalove.zettalove.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.zettalove.zettalove.entities.UserImage;
import lombok.Data;

@Data
public class UserImageDto {
    private Long id;
    @JsonProperty("image_base64")
    private String imageBase64;
    private Integer orderIndex;

    public UserImageDto(UserImage userImage) {
        this.id = userImage.getId();
        this.imageBase64 = new String(userImage.getImageBase64());
        this.orderIndex = userImage.getOrderIndex();
    }
}
