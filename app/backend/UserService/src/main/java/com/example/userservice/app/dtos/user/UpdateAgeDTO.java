package com.example.userservice.app.dtos.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class UpdateAgeDTO {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @Min(value = 0, message = "Age must be positive")
    private Integer age;

    public UpdateAgeDTO() {
    }

    public UpdateAgeDTO(UUID userId, Integer age) {
        this.userId = userId;
        this.age = age;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
