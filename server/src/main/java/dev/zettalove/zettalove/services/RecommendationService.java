package dev.zettalove.zettalove.services;

import dev.zettalove.zettalove.entities.Interest;
import dev.zettalove.zettalove.entities.User;
import dev.zettalove.zettalove.enums.Gender;
import dev.zettalove.zettalove.enums.GenderPreference;
import dev.zettalove.zettalove.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    @Transactional
    public void triggerRecommendations(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<User> allUsers = userRepository.findAll();
        Set<User> swipedUsers = user.getSwiped();

        Set<Interest> userPreferences = user.getInterests();
        LocalDate userMinDateOfBirth = calculateDateOfBirth(user.getDesiredMinAge());
        LocalDate userMaxDateOfBirth = calculateDateOfBirth(user.getDesiredMaxAge());
        GenderPreference userGenderPreference = user.getDesiredGender();

        List<User> recommendedUsers = allUsers.stream()
                .filter(other -> !user.equals(other))
                .filter(other -> !swipedUsers.contains(other))
                .filter(other -> isAgeCompatible(other.getDateOfBirth(), userMinDateOfBirth, userMaxDateOfBirth))
                .filter(other -> hasMatchingPreferences(userPreferences, other.getInterests()))
                .filter(other -> isGenderCompatible(other.getGender(), userGenderPreference))
                .collect(Collectors.toList());

        user.getRecommended().clear();
        user.getRecommended().addAll(recommendedUsers);

        String recommendedUserIds = recommendedUsers.stream()
                .map(recommendedUser -> recommendedUser.getId().toString())
                .collect(Collectors.joining(","));
        redisTemplate.opsForValue().set(user.getId().toString(), recommendedUserIds);
        userRepository.save(user);
    }

    private boolean isGenderCompatible(Gender otherGender, GenderPreference userGenderPreference) {
        return userGenderPreference == GenderPreference.BOTH ||
                (userGenderPreference == GenderPreference.MALE && otherGender == Gender.MALE) ||
                (userGenderPreference == GenderPreference.FEMALE && otherGender == Gender.FEMALE);
    }

    private boolean hasMatchingPreferences(Set<Interest> userPreferences, Set<Interest> otherPreferences) {
        Set<String> userPreferenceNames = userPreferences.stream().map(Interest::getName).collect(Collectors.toSet());
        Set<String> otherPreferenceNames = otherPreferences.stream().map(Interest::getName).collect(Collectors.toSet());
        return !Collections.disjoint(userPreferenceNames, otherPreferenceNames);
    }

    private boolean isAgeCompatible(LocalDate dateOfBirth, LocalDate minDateOfBirth, LocalDate maxDateOfBirth) {
        int age = dateOfBirth.until(LocalDate.now(ZoneId.systemDefault())).getYears();
        int minAge = minDateOfBirth.until(LocalDate.now(ZoneId.systemDefault())).getYears();
        int maxAge = maxDateOfBirth.until(LocalDate.now(ZoneId.systemDefault())).getYears();
        return age >= minAge && age <= maxAge;
    }

    private LocalDate calculateDateOfBirth(int age) {
        return LocalDate.now().minusYears(age);
    }

}
