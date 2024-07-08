package dev.zettalove.zettalove.config;


import dev.zettalove.zettalove.entities.Interest;
import dev.zettalove.zettalove.repositories.InterestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.List;

@Configuration
public class InterestConfig {

    @Bean
    public CommandLineRunner commandLineRunner(InterestRepository interestRepository) {
        return args -> {
            List<Interest> interests = Arrays.asList(
                    Interest.builder().name("music").build(),
                    Interest.builder().name("movies").build(),
                    Interest.builder().name("sports").build(),
                    Interest.builder().name("travel").build(),
                    Interest.builder().name("reading").build(),
                    Interest.builder().name("cooking").build(),
                    Interest.builder().name("gaming").build(),
                    Interest.builder().name("fitness").build(),
                    Interest.builder().name("photography").build(),
                    Interest.builder().name("programming").build()
                    );

            interestRepository.saveAll(interests);
            System.out.println("Interests have been initialized.");
        };
    }
}