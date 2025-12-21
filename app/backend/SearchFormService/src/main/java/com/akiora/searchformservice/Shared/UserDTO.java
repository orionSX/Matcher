package com.akiora.searchformservice.Shared;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@Data
public class UserDTO {
    public record Social(String platform, String url) {
    }
    public enum UserType {
        DEFAULT,
        PLAYER,
        MEDIA
    }


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
}