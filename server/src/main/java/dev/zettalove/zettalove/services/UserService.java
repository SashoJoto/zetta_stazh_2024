package dev.zettalove.zettalove.services;

import dev.zettalove.zettalove.chat.WebSocketClientService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.zettalove.zettalove.chat.entities.ChatMessage;
import dev.zettalove.zettalove.chat.entities.ChatUser;
import dev.zettalove.zettalove.dto.UserDto;
import dev.zettalove.zettalove.dto.UserImageDto;
import dev.zettalove.zettalove.entities.Interest;
import dev.zettalove.zettalove.entities.User;
import dev.zettalove.zettalove.entities.UserImage;
import dev.zettalove.zettalove.enums.UserStatus;
import dev.zettalove.zettalove.exceptions.accountsetup.ImageCountException;
import dev.zettalove.zettalove.exceptions.accountsetup.ImagesSetupDoneException;
import dev.zettalove.zettalove.exceptions.accountsetup.InterestNotFoundException;
import dev.zettalove.zettalove.exceptions.accountsetup.InterestSetupDoneException;
import dev.zettalove.zettalove.exceptions.registerexceptions.EmailFormatException;
import dev.zettalove.zettalove.exceptions.registerexceptions.EmailTakenException;
import dev.zettalove.zettalove.exceptions.userexceptions.UserAlreadyLikedException;
import dev.zettalove.zettalove.exceptions.userexceptions.UserNotFoundException;
import dev.zettalove.zettalove.exceptions.userimageexceptions.UserImageCouldNotBeRemovedException;
import dev.zettalove.zettalove.exceptions.userimageexceptions.UserImageNotFoundException;
import dev.zettalove.zettalove.repositories.InterestRepository;
import dev.zettalove.zettalove.repositories.UserRepository;
import dev.zettalove.zettalove.requests.InitialInterestsRequest;
import dev.zettalove.zettalove.requests.RegisterUserRequest;
import dev.zettalove.zettalove.security.KeycloakProvider;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {


    @Value("${jwt.admin-client.realm}")
    private String realmName;

    private final KeycloakProvider keycloakProvider;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final WebSocketClientService webSocketClientService;
    private final StringRedisTemplate redisTemplate;
    private final RecommendationService recommendationService;
    private final InterestRepository interestRepository;

    @Value("${chat.url}")
    private String chatUrl;

    public void registerUser(RegisterUserRequest registerRequest) {

        //TODO password validation

        UserRepresentation userRepresentation = toUserRepresentation(registerRequest);
        Keycloak keycloak = keycloakProvider.getKeycloakInstance();

        System.out.println("Creating user with representation: " + userRepresentation.getEmail());
        System.out.println(userRepresentation.getFirstName());
        System.out.println(userRepresentation.getLastName());
        System.out.println(userRepresentation.getUsername());

        Response response = keycloak.realm(realmName).users().create(userRepresentation);

        String responseBody = response.readEntity(String.class);
        System.out.println(responseBody);

        if (response.getStatus() == 201) {
            String userId = keycloak.realm(realmName).users().search(userRepresentation.getEmail()).get(0).getId();
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(registerRequest.getPassword());
            credential.setTemporary(false);
            keycloak.realm(realmName).users().get(userId).resetPassword(credential);

        } else {
            System.err.println("User creation failed.");
            // System.err.println("Response body: " + responseBody);
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(responseBody);
                JsonNode errorMessage = rootNode.path("errorMessage");
                if ("User exists with same email".equals(errorMessage.asText())) {
                    throw new EmailTakenException();
                } else if ("error-invalid-email".equals(errorMessage.asText())) {
                    throw new EmailFormatException();
                }
            } catch (IOException e) {
                System.err.println("Error parsing response body: " + e.getMessage());
                throw new RuntimeException("Error parsing response body", e);
            }
            System.err.println("Failed to create user: " + response.getStatusInfo());
            throw new RuntimeException("Failed to create user: " + response.getStatusInfo().toString());
        }

        UserRepresentation user = keycloak.realm(realmName).users().search(registerRequest.getEmail()).get(0);
        User user1 = User.builder()
                .Id(UUID.fromString(user.getId()))
                .profileStatus(UserStatus.ACCOUNT_NOT_COMPLETE)
                .build();
        userRepository.save(user1);
        addUserToChatSystem(user1);
    }

    @Transactional
    public void initialInterestsSetup(InitialInterestsRequest request, Authentication authentication) {
        User user = userRepository.findById(getSubjectIdFromAuthentication(authentication))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getProfileStatus().equals(UserStatus.ACTIVE) || user.getProfileStatus().equals(UserStatus.IMAGES_MISSING)){
            throw new InterestSetupDoneException();
        }

        Set<Interest> userInterests = new HashSet<Interest>();

        for (String interestName : request.getInterests()) {
            String normalizedInterestName = interestName.toLowerCase();
            Interest interest = interestRepository.findByName(normalizedInterestName)
                    .orElseThrow(InterestNotFoundException::new);
            userInterests.add(interest);
        }

        if (user.getProfileStatus() == UserStatus.ACCOUNT_NOT_COMPLETE) {
            user.setProfileStatus(UserStatus.IMAGES_MISSING);
        }else if (user.getProfileStatus() == UserStatus.INTERESTS_MISSING){
            user.setProfileStatus(UserStatus.ACTIVE);
        }
        user.setDateOfBirth(request.getDateOfBirth());
        user.setDescription(request.getDescription());
        user.setAddress(request.getAddress());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDesiredMinAge(request.getDesiredMinAge());
        user.setDesiredMaxAge(request.getDesiredMaxAge());
        user.setGender(request.getGender());
        user.setDesiredGender(request.getDesiredGender());
        user.setInterests(userInterests);

        userRepository.save(user);
    }

    @Transactional
    public void initialImageSetup(String[] images, Authentication authentication) {

        if (images.length < 1 || images.length > 3) {
            throw new ImageCountException();
        }

        UUID userId = getSubjectIdFromAuthentication(authentication);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getProfileStatus() == UserStatus.ACTIVE || user.getProfileStatus() == UserStatus.INTERESTS_MISSING){
            throw new ImagesSetupDoneException();
        }

        List<UserImage> userImages = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            userImages.add(UserImage.builder()
                    .imageBase64(images[i].getBytes())
                    .orderIndex(i)
                    .build());
        }

        user.getImages().addAll(userImages);

        if (user.getProfileStatus() == UserStatus.IMAGES_MISSING) {
            user.setProfileStatus(UserStatus.ACTIVE);
        } else if (user.getProfileStatus() == UserStatus.ACCOUNT_NOT_COMPLETE){
            user.setProfileStatus(UserStatus.INTERESTS_MISSING);
        }

        userRepository.save(user);
    }

    public List<UserDto> getAllUsers() {
        Keycloak keycloak = keycloakProvider.getKeycloakInstance();
        return userRepository.findAll().stream()
                .map(user -> {
                    UserRepresentation keycloakUser = keycloak.realm(realmName).users().get(user.getId().toString()).toRepresentation();
                    return new UserDto(user, keycloakUser);
                })
                .collect(Collectors.toList());
    }

    public UserDto getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        Keycloak keycloak = keycloakProvider.getKeycloakInstance();
        UserRepresentation keycloakUser = keycloak.realm(realmName).users().get(user.getId().toString()).toRepresentation();
        return new UserDto(user, keycloakUser);
    }

    public List<UserImageDto> getUserImages(Authentication authentication) {
        UUID userId = getSubjectIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getImages().stream().map(UserImageDto::new).collect(Collectors.toList());
    }

    public List<UserImageDto> getUserImages(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getImages().stream().map(UserImageDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void removeUserImage(Long imageId, Authentication authentication) {
        UUID userId = getSubjectIdFromAuthentication(authentication);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Optional<UserImage> imageToRemove = user.getImages().stream()
                .filter(image -> image.getId().equals(imageId))
                .findFirst();

        if (imageToRemove.isPresent()) {
            if (user.getImages().size() <= 1) {
                throw new UserImageCouldNotBeRemovedException();
            }
            user.getImages().remove(imageToRemove.get());
            userRepository.save(user);
        } else {
            throw new UserImageNotFoundException(imageId);
        }
    }

    @Transactional
    public void swipeUser(UUID swipedUserId, Authentication authentication) {
        User user = userRepository.findById(getSubjectIdFromAuthentication(authentication)).orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        User swipedUser = userRepository.findById(swipedUserId).orElseThrow(() -> new UserNotFoundException(swipedUserId));
        user.getSwiped().add(swipedUser);
        user.getRecommended().remove(swipedUser);
        userRepository.save(user);

        removeSwipedUserFromRedis(authentication, swipedUserId);
    }

    @Transactional
    public String likeUser(UUID likedUserId, Authentication authentication) {
        User user = userRepository.findById(getSubjectIdFromAuthentication(authentication))
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        User likedUser = userRepository.findById(likedUserId).orElseThrow(() -> new UserNotFoundException(likedUserId));

        if (user.getLikedUsers().contains(likedUser)) {
            throw new UserAlreadyLikedException(likedUserId);
        }

        user.getLikedUsers().add(likedUser);
        user.getSwiped().add(likedUser);
        userRepository.save(user);

        if (likedUser.getLikedUsers().contains(user)) {
            likedUser.getMatchedUsers().add(user);
            userRepository.save(likedUser);

            user.getMatchedUsers().add(likedUser);
            user.getRecommended().remove(likedUser);
            userRepository.save(user);

            removeSwipedUserFromRedis(authentication, likedUserId);

            notifyUsersForMatch(user, likedUser);
            return "matched";
        }
        return "";
    }

    @Transactional
    public Set<UserDto> getRecommendedUsers(Authentication authentication) {
        UUID userId = getSubjectIdFromAuthentication(authentication);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        // Check Redis for cached recommendations
        String recommendedUserIds = redisTemplate.opsForValue().get(String.valueOf(userId));
        Set<User> recommendedUsers = new HashSet<>();

        if (recommendedUserIds != null && !recommendedUserIds.isEmpty()) {
            List<UUID> recommendedUserIdsList = Arrays.stream(recommendedUserIds.split(","))
                    .map(UUID::fromString)
                    .collect(Collectors.toList());
            for (UUID recommendedUserId : recommendedUserIdsList) {
                User recommendedUser = userRepository.findById(recommendedUserId)
                        .orElseThrow(() -> new RuntimeException("Recommended user not found"));
                if (!user.getSwiped().contains(recommendedUser)) {
                    recommendedUsers.add(recommendedUser);
                }
            }
        }

        if (recommendedUsers.isEmpty()) {
            // Trigger recommendations and reload user to get updated recommendations
            recommendationService.triggerRecommendations(userId);
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Authenticated user not found after triggering recommendations"));
            recommendedUsers = user.getRecommended();
        }

        Keycloak keycloak = keycloakProvider.getKeycloakInstance();
        return recommendedUsers.stream()
                .map(recommendedUser -> {
                    UserRepresentation keycloakUser = keycloak.realm(realmName).users().get(recommendedUser.getId().toString()).toRepresentation();
                    return new UserDto(recommendedUser, keycloakUser);
                })
                .collect(Collectors.toSet());
    }

    public Set<User> getMatches(Authentication authentication) {
        User user = userRepository.findById(getSubjectIdFromAuthentication(authentication))
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        return user.getMatchedUsers();
    }

    public UserDto getSelf(Authentication authentication) {
        UUID userId = getSubjectIdFromAuthentication(authentication);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
        Keycloak keycloak = keycloakProvider.getKeycloakInstance();
        UserRepresentation keycloakUser = keycloak.realm(realmName).users().get(user.getId().toString()).toRepresentation();
        return new UserDto(user, keycloakUser);
    }



    public void removeSwipedUserFromRedis(Authentication authentication, UUID swipedUserId) {
        UUID userId = getSubjectIdFromAuthentication(authentication);

        String recommendedUserIds = redisTemplate.opsForValue().get(String.valueOf(userId));

        if (recommendedUserIds != null && !recommendedUserIds.isEmpty()) {
            List<String> swipedUserIdsList = new ArrayList<>(Arrays.asList(recommendedUserIds.split(",")));
            swipedUserIdsList.remove(swipedUserId.toString());
            if (swipedUserIdsList.isEmpty()) {
                redisTemplate.delete(userId.toString());
            } else {
                String updatedSwipedUserIdsString = String.join(",", swipedUserIdsList);
                redisTemplate.opsForValue().set(userId.toString(), updatedSwipedUserIdsString);
            }
        }
    }

    public UUID getSubjectIdFromAuthentication(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            String subject = jwt.getSubject(); // This is the 'sub' claim from the JWT token
            return UUID.fromString(subject);
        }
        throw new IllegalStateException("Expected JWT authentication");
    }

    private UserRepresentation toUserRepresentation(RegisterUserRequest registerRequest) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(registerRequest.getEmail());
        userRepresentation.setFirstName(registerRequest.getFirstName());
        userRepresentation.setLastName(registerRequest.getLastName());
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }



    private void addUserToChatSystem(User user) {
        UserRepresentation userRepresentation = keycloakProvider.getKeycloakInstance().realm(realmName).users().get(user.getId().toString()).toRepresentation();
        String nickname = userRepresentation.getId();
        String fullName = userRepresentation.getFirstName() + " " + userRepresentation.getLastName();
        ChatUser chatUser = new ChatUser(nickname, fullName);
        webSocketClientService.addUserToChatSystem(chatUser);
    }

     public void notifyUsersForMatch(User user, User likedUser) {
        // Fetch user ids from chat service
        ChatUser user1 = restTemplate.getForObject(chatUrl + "/users/" + user.getId(), ChatUser.class);
        ChatUser user2 = restTemplate.getForObject(chatUrl + "/users/" + likedUser.getId(), ChatUser.class);

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

        webSocketClientService.sendMessage(chatMessage);
        webSocketClientService.sendMessage(chatMessage2);
    }
}
