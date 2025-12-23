package com.akiora.formconsumer.Shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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