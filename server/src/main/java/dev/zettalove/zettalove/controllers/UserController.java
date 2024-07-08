package dev.zettalove.zettalove.controllers;

import dev.zettalove.zettalove.entities.User;
import dev.zettalove.zettalove.requests.InitialInterestsRequest;
import dev.zettalove.zettalove.requests.RegisterUserRequest;
import dev.zettalove.zettalove.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RecommendationService recommendationService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerUser(
            @RequestBody RegisterUserRequest registerRequest
    ) {
        userService.registerUser(registerRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/interests-setup")
    public ResponseEntity<?> initialInterestsSetup(
            @RequestBody InitialInterestsRequest request,
            Authentication authentication
    ) {
        userService.initialInterestsSetup(request, authentication);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/images-setup")
    public ResponseEntity<?> initialImageSetup(

    ) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/images/{imageId}")
    public void removeUserImage(@PathVariable Long userId, @PathVariable Long imageId) {
        userService.removeUserImage(userId, imageId);
    }

    @PostMapping("/{userId}/like/{likedUserId}")
    public ResponseEntity<String> likeUser(@PathVariable Long userId, @PathVariable Long likedUserId) {
        userService.likeUser(userId, likedUserId);
        userService.swipeUser(userId, likedUserId);
        return ResponseEntity.ok("User liked successfully");
    }

    @GetMapping("/{id}/recommendedUsers")
    public Set<User> getRecommended(@PathVariable Long id) {
        return userService.getRecommendedUsers(id);
    }

    @GetMapping("/{userId}/matches")
    public ResponseEntity<Set<User>> getMatches(@PathVariable Long userId) {
        Set<User> matches = userService.getMatches(userId);
        return ResponseEntity.ok(matches);
    }
}
