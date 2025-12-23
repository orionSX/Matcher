package com.example.userservice.app.dtos.auth;

import java.util.UUID;

public class AuthResponseDTO {
    private UUID userId;
    private String nickname;
    private String email;
    private String message;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(UUID userId, String nickname, String email, String message) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.message = message;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
