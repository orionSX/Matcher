package com.example.userservice.app.dtos.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserNotificationDTO {
    private String oid;
    private String nickname;
    private String action; // "create"
}
