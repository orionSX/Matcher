package com.example.userservice.app.dtos.user;

public class UpdateGenderDTO {
    private String gender;

    public UpdateGenderDTO() {
    }

    public UpdateGenderDTO(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
