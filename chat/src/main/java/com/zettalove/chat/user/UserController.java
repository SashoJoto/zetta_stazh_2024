package com.zettalove.chat.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    @ResponseBody
    public User addUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @PostMapping("/users/disconnect/{nickname}")
    @ResponseBody
    public void disconnectUser(@PathVariable String nickname) {
        userService.disconnect(nickname);
    }

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<List<User>> findConnectedUsers() {
        return ResponseEntity.ok(userService.findConnectedUsers());
    }

    @GetMapping("/users/{nickname}")
    @ResponseBody
    public ResponseEntity<User> findUserByNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.findUserByNickname(nickname));
    }
}
