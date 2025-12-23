package com.example.userservice.app.dtos.user;

import com.example.userservice.domain.models.Social;
import com.example.userservice.domain.models.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;



public class CreateUserDTO {
    @NotBlank
    private String nickname;
    @NotBlank
    @Email
    private String email;
    private String password;
    @NotNull
    private UserType type;
    @NotBlank
    private String gender;
    @Min(0)
    private Integer age;
    @NotNull
    private Map<String, Social> socials;

    private String[] accounts;
    private String[] roles;
    private String[] mediaLinks;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Map<String, Social> getSocials() {
        return socials;
    }

    public void setSocials(Map<String, Social> socials) {
        this.socials = socials;
    }

    public String[] getAccounts() {
        return accounts;
    }

    public void setAccounts(String[] accounts) {
        this.accounts = accounts;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String[] getMediaLinks() {
        return mediaLinks;
    }

    public void setMediaLinks(String[] mediaLinks) {
        this.mediaLinks = mediaLinks;
    }
}
