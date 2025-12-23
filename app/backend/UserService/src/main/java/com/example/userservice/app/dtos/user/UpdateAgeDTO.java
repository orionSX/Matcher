package com.example.userservice.app.dtos.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateAgeDTO {
    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be positive")
    private Integer age;

    public UpdateAgeDTO() {
    }

    public UpdateAgeDTO(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
