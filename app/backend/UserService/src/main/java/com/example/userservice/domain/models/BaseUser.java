package com.example.userservice.domain.models;

import com.example.userservice.domain.exceptions.DomainException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Document(collection = "users")
public abstract class BaseUser {
    @Id
    @Field(targetType = FieldType.STRING)
    protected UUID oid;
    protected LocalDateTime createdAt;
    protected String nickname;
    protected String email;
    protected String password;
    protected UserType type;
    protected String gender;
    protected Integer age;
    protected Map<String, Social> socials;

    protected BaseUser(UUID oid, LocalDateTime createdAt, String nickname, String email, String password, UserType type, String gender, Integer age, Map<String, Social> socials) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new DomainException("Nickname cannot be empty or whitespace");
        }
        this.oid = oid;
        this.createdAt = createdAt;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.type = type;
        this.gender = gender;
        this.age = age;
        this.socials = socials;
    }

    public UUID getOid() {
        return oid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserType getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public Map<String, Social> getSocials() {
        return socials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseUser baseUser = (BaseUser) o;
        return Objects.equals(oid, baseUser.oid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oid);
    }
}
