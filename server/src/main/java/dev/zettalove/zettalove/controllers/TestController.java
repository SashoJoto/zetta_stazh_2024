package dev.zettalove.zettalove.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/test")
public class TestController {

    @GetMapping
    public String test() {
        return "Hello World!";
    }

    @GetMapping("user")
    @PreAuthorize("hasRole('client_user')")
    public String testUser() {
        return "Hello User!";
    }

    @GetMapping("admin")
    @PreAuthorize("hasRole('client_admin')")
    public String testAdmin() {
        return "Hello Admin!";
    }

}
