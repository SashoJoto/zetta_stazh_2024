package dev.zettalove.zettalove.controllers;

import dev.zettalove.zettalove.entities.user.User;
import dev.zettalove.zettalove.services.RecommendationService;
import dev.zettalove.zettalove.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/server/recommendations")
public class RecommendationController {
    private final UserService userService;
    private final RecommendationService recommendationService;
    @GetMapping("/{id}")
    public void generateRecommendations(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        user.ifPresent(recommendationService::triggerRecommendations);
    }
}
