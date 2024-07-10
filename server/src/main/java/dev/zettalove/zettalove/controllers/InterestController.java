package dev.zettalove.zettalove.controllers;

import dev.zettalove.zettalove.services.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interests")
@RequiredArgsConstructor
public class InterestController {

    private final InterestService interestService;

    @GetMapping()
    public ResponseEntity<?> getAllInterests() {
        return ResponseEntity.ok(interestService.getAllInterests());
    }

}
