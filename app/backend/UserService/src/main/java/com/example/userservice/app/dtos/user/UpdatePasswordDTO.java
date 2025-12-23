package com.example.userservice.app.dtos.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public class UpdatePasswordDTO {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    public UpdatePasswordDTO() {
    }

    public UpdatePasswordDTO(UUID userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
