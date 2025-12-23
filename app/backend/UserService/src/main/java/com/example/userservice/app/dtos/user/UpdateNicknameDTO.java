package com.example.userservice.app.dtos.user;

import jakarta.validation.constraints.NotBlank;

public class UpdateNicknameDTO {
    @NotBlank(message = "Nickname is required")
    private String nickname;

    public UpdateNicknameDTO() {
    }

    public UpdateNicknameDTO(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
