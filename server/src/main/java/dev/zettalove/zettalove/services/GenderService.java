package dev.zettalove.zettalove.services;

import dev.zettalove.zettalove.entities.gender.Gender;
import dev.zettalove.zettalove.repositories.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenderService {
    private final GenderRepository genderRepository;

    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }

    public Optional<Gender> getGenderById(Long id) {
        return genderRepository.findById(id);
    }

    public Gender saveGender(Gender gender) {
        return genderRepository.save(gender);
    }

    public void deleteGender(Long id) {
        genderRepository.deleteById(id);
    }
}
