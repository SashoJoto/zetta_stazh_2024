package dev.zettalove.zettalove.controllers;

import dev.zettalove.zettalove.dto.UserDto;
import dev.zettalove.zettalove.entities.User;
import dev.zettalove.zettalove.services.RecommendationService;
import dev.zettalove.zettalove.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommendations")
public class RecommendationController {
    private final UserService userService;
    private final RecommendationService recommendationService;

    @GetMapping("/{id}")
    public void generateRecommendations(@PathVariable UUID id) {
        UserDto user = userService.getUserById(id);
        recommendationService.triggerRecommendations(user.getId());
    }
}
