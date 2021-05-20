package com.example.jwt.api;

import com.example.jwt.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/profile/{username}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable("username") String username) {
        return null;
    }
}
