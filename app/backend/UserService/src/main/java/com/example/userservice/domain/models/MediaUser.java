package com.example.userservice.domain.models;

import com.example.userservice.domain.exceptions.DomainException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class MediaUser extends BaseUser {
    private String[] mediaLinks;

    private MediaUser(UUID oid, LocalDateTime createdAt, String nickname, String email, String password, UserType type, String gender, Integer age, Map<String, Social> socials, String[] mediaLinks) {
        super(oid, createdAt, nickname, email, password, type, gender, age, socials);
        this.mediaLinks = mediaLinks;
    }

    public String[] getMediaLinks() {
        return mediaLinks;
    }

    public static MediaUser create(UUID id, LocalDateTime createdAt, String nickname, String email, String password, UserType type, String gender, Integer age, Map<String, Social> socials, String[] mediaLinks) {
        if (nickname == null || nickname.isEmpty()) throw new DomainException("Nickname cannot be empty");
        return new MediaUser(id, createdAt, nickname, email, password, type, gender, age, socials, mediaLinks);
    }

    public static MediaUser update(MediaUser oldMedia, String nickname, String email, String password, UserType type, String gender, Integer age, Map<String, Social> socials, String[] mediaLinks) {
        return create(oldMedia.getOid(), oldMedia.getCreatedAt(), nickname, email, password, type, gender, age, socials, mediaLinks);
    }
}
