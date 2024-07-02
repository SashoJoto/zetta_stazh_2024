package dev.zettalove.zettalove.services;

import dev.zettalove.zettalove.entities.user.UserImage;
import dev.zettalove.zettalove.entities.user.User;
import dev.zettalove.zettalove.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void addUserImage(Long userId, UserImage userImage) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            user.get().addImage(userImage);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }


    public List<UserImage> getUserImages(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<UserImage> images = user.getImages();
        images.sort(Comparator.comparingInt(UserImage::getOrderIndex));
        return images;
    }

    public void removeUserImage(Long userId, Long imageId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.getImages().removeIf(image -> image.getId().equals(imageId));
        user.getImages().sort(Comparator.comparingInt(UserImage::getOrderIndex));
        for (int i = 0; i < user.getImages().size(); i++) {
            user.getImages().get(i).setOrderIndex(i);
        }
        userRepository.save(user);
    }

    public void likeUser(Long userId, Long likedUserId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User likedUser = userRepository.findById(likedUserId).orElseThrow(() -> new RuntimeException("Liked user not found"));

        user.getLikedUsers().add(likedUser);
        userRepository.save(user);

        if (likedUser.getLikedUsers().contains(user)) {
            //TODO
            // Match found
            // Notify both users about the match (could be through WebSocket or other means)
        }
    }

    public Set<User> getMatches(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getLikedUsers().stream()
                .filter(likedUser -> likedUser.getLikedUsers().contains(user))
                .collect(Collectors.toSet());
    }
}
