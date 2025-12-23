package com.example.userservice.app.dtos.user;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateGenderDTO {
    @NotNull(message = "User ID is required")
    private UUID userId;

    private String gender;

    public UpdateGenderDTO() {
    }

    public UpdateGenderDTO(UUID userId, String gender) {
        this.userId = userId;
        this.gender = gender;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
