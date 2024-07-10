package dev.zettalove.zettalove.services;

import dev.zettalove.zettalove.dto.InterestDto;
import dev.zettalove.zettalove.entities.Interest;
import dev.zettalove.zettalove.repositories.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;


    public List<InterestDto> getAllInterests() {
        List<Interest> interests = interestRepository.findAll();
        return interests.stream().map(InterestDto::new).toList();
    }

}
