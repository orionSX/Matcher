package com.example.userservice.app.dtos.user;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateNicknameDTO {
    @NotNull(message = "User ID is required")
    private UUID userId;

    private String nickname;

    public UpdateNicknameDTO() {
    }

    public UpdateNicknameDTO(UUID userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
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
}
