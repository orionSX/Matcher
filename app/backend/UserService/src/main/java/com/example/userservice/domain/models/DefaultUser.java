package com.example.userservice.domain.models;

import com.example.userservice.domain.exceptions.DomainException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class DefaultUser extends BaseUser {

    private DefaultUser(UUID oid, LocalDateTime createdAt, String nickname, String email, UserType type, String gender, Integer age, Map<String, Social> socials) {
        super(oid, createdAt, nickname, email, type, gender, age, socials);
    }

    public static DefaultUser create(UUID id, LocalDateTime createdAt, String nickname, String email, UserType type, String gender, Integer age, Map<String, Social> socials) {
        if (nickname == null || nickname.isEmpty()) throw new DomainException("Nickname cannot be empty");
        return new DefaultUser(id, createdAt, nickname, email, type, gender, age, socials);
    }

    public static DefaultUser update(DefaultUser oldUser, String nickname, String email, UserType type, String gender, Integer age, Map<String, Social> socials) {
        return create(oldUser.getOid(), oldUser.getCreatedAt(), nickname, email, type, gender, age, socials);
    }
}
