package dev.zettalove.zettalove.services;

import dev.zettalove.zettalove.entities.preference.Preference;
import dev.zettalove.zettalove.repositories.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;

    public List<Preference> getAllPreferences() {
        return preferenceRepository.findAll();
    }

    public Optional<Preference> getPreferenceById(Long id) {
        return preferenceRepository.findById(id);
    }

    public Preference savePreference(Preference preference) {
        return preferenceRepository.save(preference);
    }

    public void deletePreference(Long id) {
        preferenceRepository.deleteById(id);
    }
}
