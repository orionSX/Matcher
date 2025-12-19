package com.example.userservice.app.dtos.user;

import com.example.userservice.domain.models.Social;
import com.example.userservice.domain.models.UserType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class ResponseUserDTO {
    private UUID oid;
    private LocalDateTime createdAt;
    private String nickname;
    private String email;
    private UserType type;
    private String gender;
    private Integer age;
    private Map<String, Social> socials;

    private String[] accounts;
    private String[] roles;
    private String[] mediaLinks;

    public ResponseUserDTO() {
    }

    public ResponseUserDTO(UUID oid, LocalDateTime createdAt, String nickname, String email, UserType type, String gender, Integer age, Map<String, Social> socials, String[] accounts, String[] roles, String[] mediaLinks) {
        this.oid = oid;
        this.createdAt = createdAt;
        this.nickname = nickname;
        this.email = email;
        this.type = type;
        this.gender = gender;
        this.age = age;
        this.socials = socials;
        this.accounts = accounts;
        this.roles = roles;
        this.mediaLinks = mediaLinks;
    }

    public UUID getOid() {
        return oid;
    }

    public void setOid(UUID oid) {
        this.oid = oid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
