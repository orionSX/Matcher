package com.example.notifier.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    private String id;
    private String userId;
    private String telegramChatId;
    private String email;
    private String message;
    private NotificationType type;
    private NotificationStatus status;
    private java.time.LocalDateTime sentAt;
    private boolean enableEmailNotifications;
    private boolean enableTelegramNotifications;

    public enum NotificationType {
        EMAIL,
        TELEGRAM
    }

    public enum NotificationStatus {
        PENDING,
        SENT,
        FAILED
    }
}
