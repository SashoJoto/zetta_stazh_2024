package dev.zettalove.zettalove.controllers;

import dev.zettalove.zettalove.entities.gender.Gender;
import dev.zettalove.zettalove.services.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genders")
@RequiredArgsConstructor
public class GenderController {
    private final GenderService genderService;

    @GetMapping
    public List<Gender> getAllGenders() {
        return genderService.getAllGenders();
    }

    @GetMapping("/{id}")
    public Optional<Gender> getGenderById(@PathVariable Long id) {
        return genderService.getGenderById(id);
    }

    @PostMapping
    public Gender createGender(@RequestBody Gender gender) {
        return genderService.saveGender(gender);
    }

    @DeleteMapping("/{id}")
    public void deleteGender(@PathVariable Long id) {
        genderService.deleteGender(id);
    }
}
