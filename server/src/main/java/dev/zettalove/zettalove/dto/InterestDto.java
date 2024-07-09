package dev.zettalove.zettalove.dto;

import dev.zettalove.zettalove.entities.Interest;
import lombok.Data;

@Data
public class InterestDto {
    private Long id;
    private String name;

    public InterestDto(Interest interest) {
        this.id = interest.getId();
        this.name = interest.getName();
    }
}
