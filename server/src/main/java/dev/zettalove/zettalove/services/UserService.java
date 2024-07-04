package dev.zettalove.zettalove.services;

import dev.zettalove.zettalove.chat.WebSocketClientService;
import dev.zettalove.zettalove.chat.entities.ChatMessage;
import dev.zettalove.zettalove.chat.entities.ChatUser;
import dev.zettalove.zettalove.chat.entities.Status;
import dev.zettalove.zettalove.entities.user.UserImage;
import dev.zettalove.zettalove.entities.user.User;
import dev.zettalove.zettalove.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final WebSocketClientService webSocketClientService;

    @Value("${chat.url}")
    private String chatUrl;

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
        User savedUser = userRepository.save(user);
        addUserToChatSystem(savedUser);
        return savedUser;
    }

    private void addUserToChatSystem(User user) {
        String nickname = user.getNickname();
        String fullName = user.getFirstName() + " " + user.getLastName();
        ChatUser chatUser = new ChatUser(nickname, fullName);
        webSocketClientService.addUserToChatSystem(chatUser);
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
            likedUser.getMatchedUsers().add(user);
            user.getMatchedUsers().add(likedUser);
            notifyUsersForMatch(user, likedUser);
        }
    }

    public void swipeUser(Long userId, Long swipedUserId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        User swipedUser = userRepository.findById(swipedUserId).orElseThrow(() -> new RuntimeException("Swiped user not found"));
        user.getSwiped().add(swipedUser);
        user.getRecommended().remove(swipedUser);
        userRepository.save(user);
    }
     public void notifyUsersForMatch(User user, User likedUser) {
        // Fetch user ids from chat service
        ChatUser user1 = restTemplate.getForObject(chatUrl + "/users/" + user.getNickname(), ChatUser.class);
        ChatUser user2 = restTemplate.getForObject(chatUrl + "/users/" + likedUser.getNickname(), ChatUser.class);

        if (user1 == null || user2 == null) {
            throw new RuntimeException("User not found in chat service");
        }

        String userId1 = user1.getNickName();
        String userId2 = user2.getNickName();

        // Create chat message for notification to user 1
        ChatMessage chatMessage = ChatMessage.builder()
                .chatId(userId1 + "_" + userId2) // or some other logic to generate a chatId
                .senderId(userId1)
                .recipientId(userId2)
                .content("You have a new match!")
                .timestamp(new Date())
                .build();

        // Create chat message for notification to user 2
        ChatMessage chatMessage2 = ChatMessage.builder()
                .chatId(userId1 + "_" + userId2)
                .senderId(userId2)
                .recipientId(userId1)
                .content("You have a new match!")
                .timestamp(new Date())
                .build();

        // Post the chat message to the chat service to process and notify users
        webSocketClientService.sendMessage(chatMessage);
        webSocketClientService.sendMessage(chatMessage2);
    }

    public Set<User> getMatches(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getMatchedUsers();
    }
}
