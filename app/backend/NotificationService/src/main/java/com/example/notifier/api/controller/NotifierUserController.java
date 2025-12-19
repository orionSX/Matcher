package com.example.notifier.api.controller;

import com.example.notifier.domain.User;
import com.example.notifier.domain.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/notifier-users")
public class NotifierUserController {
    private final UserRepository userRepository;

    public NotifierUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody Map<String, Object> userData) {
        User user = new User();
        user.setId((String) userData.get("oid"));
        user.setUsername((String) userData.get("nickname"));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/telegram")
    public ResponseEntity<Void> updateTelegramChatId(@PathVariable String userId, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setTelegramId(body.get("telegramChatId"));
        user.setEnableTelegramNotifications(true);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{userId}/telegram-disable")
    public ResponseEntity<Void> disableTelegramNotifications(@PathVariable String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setEnableTelegramNotifications(false);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/email")
    public ResponseEntity<Void> enableEmailNotifications(@PathVariable String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setEnableEmailNotifications(true);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/email-disable")
    public ResponseEntity<Void> disableEmailNotifications(@PathVariable String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setEnableEmailNotifications(false);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
