package dev.zettalove.zettalove.controllers;

import dev.zettalove.zettalove.entities.user.UserImage;
import dev.zettalove.zettalove.entities.user.User;
import dev.zettalove.zettalove.services.RecommendationService;
import dev.zettalove.zettalove.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/server/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RecommendationService recommendationService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{id}/images")
    public UserImage addUserImage(@PathVariable Long id, @RequestBody UserImage userImage) {
        userService.addUserImage(id, userImage);
        return userImage;
    }

    @GetMapping("/{id}/images")
    public List<UserImage> getUserImages(@PathVariable Long id) {
        return userService.getUserImages(id);
    }

    @DeleteMapping("/{userId}/images/{imageId}")
    public void removeUserImage(@PathVariable Long userId, @PathVariable Long imageId) {
        userService.removeUserImage(userId, imageId);
    }

    @PostMapping("/{userId}/like/{likedUserId}")
    public ResponseEntity<String> likeUser(@PathVariable Long userId, @PathVariable Long likedUserId) {
        userService.likeUser(userId, likedUserId);
        return ResponseEntity.ok("User liked successfully");
    }

    @GetMapping("/{id}/recommendations")
    public void generateRecommendations(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        user.ifPresent(recommendationService::getRecommendedProfiles);
    }

    @GetMapping("/{userId}/matches")
    public ResponseEntity<Set<User>> getMatches(@PathVariable Long userId) {
        Set<User> matches = userService.getMatches(userId);
        return ResponseEntity.ok(matches);
    }
}
