package dev.zettalove.zettalove.controllers;

import dev.zettalove.zettalove.entities.User;
import dev.zettalove.zettalove.requests.InitialInterestsRequest;
import dev.zettalove.zettalove.requests.RegisterUserRequest;
//import dev.zettalove.zettalove.services.RecommendationService;
import dev.zettalove.zettalove.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//    private final RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
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
            @RequestBody String[] images,
            Authentication authentication
    ) {
        userService.initialImageSetup(images, authentication);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<?> removeUserImage(
            @PathVariable Long imageId,
            Authentication authentication
    ) {
        userService.removeUserImage(imageId, authentication);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/swipe/{swipedUserId}")
    public ResponseEntity<?> swipeUser(
            @PathVariable UUID swipedUserId,
            Authentication authentication
    ) {
        userService.swipeUser(swipedUserId, authentication);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like/{likedUserId}")
    public ResponseEntity<?> likeUser(
            @PathVariable UUID likedUserId,
            Authentication authentication
    ) {
        userService.likeUser(likedUserId, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recommendedUsers")
    public Set<User> getRecommended(Authentication authentication) {
        return userService.getRecommendedUsers(authentication);
    }



    @GetMapping("/{userId}/matches")
    public ResponseEntity<Set<User>> getMatches(@PathVariable UUID userId) {
        Set<User> matches = userService.getMatches(userId);
        return ResponseEntity.ok(matches);
    }
}
