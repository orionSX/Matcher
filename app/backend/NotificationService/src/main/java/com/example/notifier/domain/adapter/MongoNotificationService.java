package com.example.notifier.domain.adapter;

import com.example.notifier.domain.Notification;
import com.example.notifier.domain.NotificationService;
import org.springframework.stereotype.Service;
import com.example.notifier.app.service.EmailNotificationService;

@Service
public class MongoNotificationService implements NotificationService {

    private final MongoNotificationRepositoryAdapter notificationRepositoryAdapter;
    private final EmailNotificationService emailNotificationService;

    public MongoNotificationService(MongoNotificationRepositoryAdapter notificationRepositoryAdapter, EmailNotificationService emailNotificationService) {
        this.notificationRepositoryAdapter = notificationRepositoryAdapter;
        this.emailNotificationService = emailNotificationService;
    }

    @Override
    public Notification sendNotification(Notification notification) {
        System.out.println("Mongo Notification Service: Sending and saving notification for userId=" + notification.getUserId() + ", telegramChatId=" + notification.getTelegramChatId());
        if (notification.getType() == Notification.NotificationType.EMAIL) {
            // email отправка
            String email = notification.getEmail();
            if (email != null && !email.isEmpty()) {
                emailNotificationService.sendEmail(email, "Уведомление", notification.getMessage());
            }
        }
        notification.setStatus(Notification.NotificationStatus.SENT);
        return notificationRepositoryAdapter.save(notification);
    }
}