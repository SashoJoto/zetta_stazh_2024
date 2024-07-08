package dev.zettalove.zettalove.services;

import dev.zettalove.zettalove.entities.preference.Preference;
import dev.zettalove.zettalove.entities.user.User;
import dev.zettalove.zettalove.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    public void triggerRecommendations(User user) {
        List<User> allUsers = userRepository.findAll(); // Consider pagination or batch processing for large datasets
        Set<User> swipedUsers = user.getSwiped(); // Ensure this is fetched within the transaction

        List<Preference> userPreferences = user.getPreferences();
        LocalDate userMinDateOfBirth = calculateDateOfBirth(user.getMaxAge());
        LocalDate userMaxDateOfBirth = calculateDateOfBirth(user.getMinAge());

        List<User> recommendedUsers = allUsers.stream()
                .filter(other -> !user.equals(other))
                .filter(other -> !swipedUsers.contains(other))
                .filter(other -> isAgeCompatible(other.getDateOfBirth(), userMinDateOfBirth, userMaxDateOfBirth))
                .filter(other -> hasMatchingPreferences(userPreferences, other.getPreferences()))
                .collect(Collectors.toList());

        user.getRecommended().addAll(recommendedUsers);

        // Write to Redis with userId as key and recommended user IDs as value (comma-separated string)
        String recommendedUserIds = recommendedUsers.stream()
                .map(recommendedUser -> recommendedUser.getId().toString())
                .collect(Collectors.joining(","));
        redisTemplate.opsForValue().set(user.getId().toString(), recommendedUserIds);
        userRepository.save(user);
    }


    private boolean hasMatchingPreferences(List<Preference> userPreferences, List<Preference> otherPreferences) {
        Set<String> userPreferenceNames = userPreferences.stream().map(Preference::getName).collect(Collectors.toSet());
        Set<String> otherPreferenceNames = otherPreferences.stream().map(Preference::getName).collect(Collectors.toSet());
        return !Collections.disjoint(userPreferenceNames, otherPreferenceNames);
    }

    private boolean isAgeCompatible(Date dateOfBirth, LocalDate minDateOfBirth, LocalDate maxDateOfBirth) {
        LocalDate localDateOfBirth = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (localDateOfBirth.isAfter(minDateOfBirth) || localDateOfBirth.isEqual(minDateOfBirth)) &&
               (localDateOfBirth.isBefore(maxDateOfBirth) || localDateOfBirth.isEqual(maxDateOfBirth));
    }

    private LocalDate calculateDateOfBirth(int age) {
        return LocalDate.now().minusYears(age);
    }

    public List<User> getRecommendedUsers(Long userId) {
        String recommendedUserIds = redisTemplate.opsForValue().get(userId.toString());
        if (recommendedUserIds == null || recommendedUserIds.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(recommendedUserIds.split(","))
                .map(Long::parseLong)
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
