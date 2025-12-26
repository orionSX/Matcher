package com.example.userservice.app.dtos.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserNotificationDTO {
    private String userId;
    private String action; // "telegram", "telegram-disable", "email", "email-disable"
    private String value; // для telegram - telegramChatId
}
