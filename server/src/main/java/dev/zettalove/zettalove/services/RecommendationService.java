package dev.zettalove.zettalove.services;

import dev.zettalove.zettalove.entities.preference.Preference;
import dev.zettalove.zettalove.entities.user.User;
import dev.zettalove.zettalove.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final UserService userService;
    private final UserRepository userRepository;

    public void getRecommendedProfiles(User user) {
        System.out.println("Getting recommended profiles for user: " + user.getEmail());
        List<User> allUsers = userService.getAllUsers();
        Set<User> swipedUsers = user.getSwiped(); // Ensure this is fetched within the transaction

        List<Preference> userPreferences = user.getPreferences();
        LocalDate userMinDateOfBirth = calculateDateOfBirth(user.getMaxAge()); // Note: maxAge to get minDateOfBirth
        LocalDate userMaxDateOfBirth = calculateDateOfBirth(user.getMinAge()); // Note: minAge to get maxDateOfBirth

        List<User> recommendedUsers = allUsers.stream()
                .filter(other -> !user.equals(other))
                .filter(other -> !swipedUsers.contains(other))
                .filter(other -> isAgeCompatible(other.getDateOfBirth(), userMinDateOfBirth, userMaxDateOfBirth))
                .filter(other -> hasMatchingPreferences(userPreferences, other.getPreferences()))
                .toList();

        user.getRecommended().addAll(recommendedUsers);
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
}
