package dev.zettalove.zettalove.controllers;

import dev.zettalove.zettalove.entities.preference.Preference;
import dev.zettalove.zettalove.services.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/server/preferences")
@RequiredArgsConstructor
public class PreferenceController {
    private final PreferenceService preferenceService;

    @GetMapping
    public List<Preference> getAllPreferences() {
        return preferenceService.getAllPreferences();
    }

    @GetMapping("/{id}")
    public Optional<Preference> getPreferenceById(@PathVariable Long id) {
        return preferenceService.getPreferenceById(id);
    }

    @PostMapping
    public Preference createPreference(@RequestBody Preference preference) {
        return preferenceService.savePreference(preference);
    }

    @DeleteMapping("/{id}")
    public void deletePreference(@PathVariable Long id) {
        preferenceService.deletePreference(id);
    }
}
