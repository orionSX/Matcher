
package com.example.userservice.api.controllers;

import com.example.userservice.app.dtos.user.*;
import com.example.userservice.app.services.IUserService;
import com.example.userservice.domain.models.UserType;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final IUserService userService;

    public UsersController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ResponseUserDTO>> getUsersByType(@PathVariable UserType type) {
        return ResponseEntity.ok(userService.getUsersByType(type));
    }

    @GetMapping("/{oid}")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable UUID oid) {
        return ResponseEntity.ok(userService.getUserById(oid));
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> createUser(@Valid @RequestBody CreateUserDTO dto) {
        return new ResponseEntity<>(userService.createUser(dto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ResponseUserDTO> updateUser(@Valid @RequestBody UpdateUserDTO dto) {
        return ResponseEntity.ok(userService.updateUser(dto));
    }

    @DeleteMapping("/{oid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID oid) {
        userService.deleteUser(oid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{oid}/email")
    public ResponseEntity<String> getUserEmailById(@PathVariable UUID oid) {
        ResponseUserDTO user = userService.getUserById(oid);
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.getEmail());
    }

    @PatchMapping("/{userId}/nickname")
    public ResponseEntity<ResponseUserDTO> updateNickname(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateNicknameDTO dto) {
        return ResponseEntity.ok(userService.updateNickname(userId, dto.getNickname()));
    }

    @PatchMapping("/{userId}/email")
    public ResponseEntity<ResponseUserDTO> updateEmail(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateEmailDTO dto) {
        return ResponseEntity.ok(userService.updateEmail(userId, dto.getEmail()));
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<ResponseUserDTO> updatePassword(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdatePasswordDTO dto) {
        return ResponseEntity.ok(userService.updatePassword(userId, dto.getPassword()));
    }

    @PatchMapping("/{userId}/gender")
    public ResponseEntity<ResponseUserDTO> updateGender(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateGenderDTO dto) {
        return ResponseEntity.ok(userService.updateGender(userId, dto.getGender()));
    }

    @PatchMapping("/{userId}/age")
    public ResponseEntity<ResponseUserDTO> updateAge(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateAgeDTO dto) {
        return ResponseEntity.ok(userService.updateAge(userId, dto.getAge()));
    }

    @PatchMapping("/{userId}/socials")
    public ResponseEntity<ResponseUserDTO> updateSocials(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateSocialsDTO dto) {
        return ResponseEntity.ok(userService.updateSocials(userId, dto.getSocials()));
    }
}
