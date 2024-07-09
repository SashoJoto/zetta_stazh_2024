package dev.zettalove.zettalove.controllers;

import dev.zettalove.zettalove.entities.Interest;
import dev.zettalove.zettalove.services.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/server/preferences")
@RequiredArgsConstructor
public class PreferenceController {
    private final PreferenceService preferenceService;

    @GetMapping
    public List<Interest> getAllPreferences() {
        return preferenceService.getAllPreferences();
    }

    @GetMapping("/{id}")
    public Optional<Interest> getPreferenceById(@PathVariable Long id) {
        return preferenceService.getPreferenceById(id);
    }

    @PostMapping
    public Interest createPreference(@RequestBody Interest interest) {
        return preferenceService.savePreference(interest);
    }

    @DeleteMapping("/{id}")
    public void deletePreference(@PathVariable Long id) {
        preferenceService.deletePreference(id);
    }
}
