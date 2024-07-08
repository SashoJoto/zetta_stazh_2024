package dev.zettalove.zettalove.services;

import dev.zettalove.zettalove.entities.Interest;
import dev.zettalove.zettalove.repositories.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreferenceService {
    private final InterestRepository interestRepository;

    public List<Interest> getAllPreferences() {
        return interestRepository.findAll();
    }

    public Optional<Interest> getPreferenceById(Long id) {
        return interestRepository.findById(id);
    }

    public Interest savePreference(Interest interest) {
        return interestRepository.save(interest);
    }

    public void deletePreference(Long id) {
        interestRepository.deleteById(id);
    }
}
