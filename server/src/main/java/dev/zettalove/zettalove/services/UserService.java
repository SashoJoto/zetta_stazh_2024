package dev.zettalove.zettalove.services;

//import dev.zettalove.zettalove.chat.WebSocketClientService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zettalove.zettalove.entities.Interest;
import dev.zettalove.zettalove.entities.User;
import dev.zettalove.zettalove.enums.UserStatus;
import dev.zettalove.zettalove.exceptions.accountsetup.InterestNotFoundException;
import dev.zettalove.zettalove.exceptions.registerexceptions.EmailFormatException;
import dev.zettalove.zettalove.exceptions.registerexceptions.EmailTakenException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class UserService {


    @Value("${jwt.admin-client.realm}")
    private String realmName;

    private final KeycloakProvider keycloakProvider;
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;

    //private final RestTemplate restTemplate;
    //private final WebSocketClientService webSocketClientService;

//    @Value("${chat.url}")
//    private String chatUrl;

//    public User saveUser(User user) {
//        User savedUser = userRepository.save(user);
//        addUserToChatSystem(savedUser);
//        return savedUser;
//    }
//
//    private void addUserToChatSystem(User user) {
//        String nickname = user.getNickname();
//        String fullName = user.getFirstName() + " " + user.getLastName();
//        ChatUser chatUser = new ChatUser(nickname, fullName);
//        webSocketClientService.addUserToChatSystem(chatUser);
//    }

    //TODO USER SETUP full three steps

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
            String userId = keycloak.realm(realmName).users().search(userRepresentation.getUsername()).get(0).getId();
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
        User.builder()
                .Id(UUID.fromString(user.getId()))
                .profileStatus(UserStatus.ACCOUNT_NOT_COMPLETE)
                .build();
    }

    //        System.out.println(authentication.getName() + " NAME"); //USER EMAIL
    //        System.out.println(authentication.getCredentials().toString() + " CREDENTIALS");
    //        System.out.println(authentication.getDetails() + " DETAILS");

    @Transactional
    public void initialInterestsSetup(InitialInterestsRequest request, Authentication authentication) {
        User user = userRepository.findById(getSubjectIdFromAuthentication(authentication))
                .orElseThrow(() -> new RuntimeException("User not found"));

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




    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
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



//
//    public Optional<User> getUserById(Long id) {
//        return userRepository.findById(id);
//    }
//
//    public User getUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    public User saveUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }
//
//    public void addUserImage(Long userId, UserImage userImage) {
//        Optional<User> user = userRepository.findById(userId);
//        if (user.isPresent()) {
//            user.get().addImage(userImage);
//        } else {
//            throw new IllegalArgumentException("User not found");
//        }
//    }
//
//
//    public List<UserImage> getUserImages(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
//        List<UserImage> images = user.getImages();
//        images.sort(Comparator.comparingInt(UserImage::getOrderIndex));
//        return images;
//    }
//
//    public void removeUserImage(Long userId, Long imageId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
//        user.getImages().removeIf(image -> image.getId().equals(imageId));
//        user.getImages().sort(Comparator.comparingInt(UserImage::getOrderIndex));
//        for (int i = 0; i < user.getImages().size(); i++) {
//            user.getImages().get(i).setOrderIndex(i);
//        }
//        userRepository.save(user);
//    }
//
//    public void likeUser(Long userId, Long likedUserId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        User likedUser = userRepository.findById(likedUserId).orElseThrow(() -> new RuntimeException("Liked user not found"));
//
//        user.getLikedUsers().add(likedUser);
//        userRepository.save(user);
//
//        if (likedUser.getLikedUsers().contains(user)) {
//            //TODO
//            // Match found
//            // Notify both users about the match (could be through WebSocket or other means)
//        }
//    }
//
//    public Set<User> getMatches(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        return user.getLikedUsers().stream()
//                .filter(likedUser -> likedUser.getLikedUsers().contains(user))
//                .collect(Collectors.toSet());
//    }
}
