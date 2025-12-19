package com.example.notifier.api.dto;

import com.example.notifier.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDTO {
    private String id;
    private String userId;
    private String telegramChatId;
    private String message;
    private Notification.NotificationType type;
    private Notification.NotificationStatus status;
}