package com.example.userservice.app.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateEmailDTO {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @Email(message = "Email should be valid")
    private String email;

    public UpdateEmailDTO() {
    }

    public UpdateEmailDTO(UUID userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
