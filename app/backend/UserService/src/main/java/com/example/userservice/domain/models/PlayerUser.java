package com.example.userservice.domain.models;

import com.example.userservice.domain.exceptions.DomainException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class PlayerUser extends BaseUser {
    private String[] accounts;
    private String[] roles;

    private PlayerUser(UUID oid, LocalDateTime createdAt, String nickname, String email, UserType type, String gender, Integer age, Map<String, Social> socials, String[] accounts, String[] roles) {
        super(oid, createdAt, nickname, email, type, gender, age, socials);
        this.accounts = accounts;
        this.roles = roles;
    }

    public String[] getAccounts() {
        return accounts;
    }

    public String[] getRoles() {
        return roles;
    }

    public static PlayerUser create(UUID id, LocalDateTime createdAt, String nickname, String email, UserType type, String gender, Integer age, Map<String, Social> socials, String[] accounts, String[] roles) {
        if (nickname == null || nickname.isEmpty()) throw new DomainException("Nickname cannot be empty");
        return new PlayerUser(id, createdAt, nickname, email, type, gender, age, socials, accounts, roles);
    }

    public static PlayerUser update(PlayerUser oldPlayer, String nickname, String email, UserType type, String gender, Integer age, Map<String, Social> socials, String[] accounts, String[] roles) {
        return create(oldPlayer.getOid(), oldPlayer.getCreatedAt(), nickname, email, type, gender, age, socials, accounts, roles);
    }
}
