package com.example.notifier.app.service;

import com.example.notifier.domain.Notification;
import com.example.notifier.domain.NotificationRepository;
import com.example.notifier.domain.NotificationService;
import java.util.List;
import java.time.LocalDateTime;
import com.example.notifier.domain.UserRepository;
import com.example.notifier.domain.User;
import com.example.notifier.app.dto.SendNotificationEventRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service

public class SendNotificationService {

    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final TelegramNotificationBot telegramNotificationBot;
    private final UserServiceClient userServiceClient;

    @Autowired
    public SendNotificationService(NotificationService notificationService, NotificationRepository notificationRepository, UserRepository userRepository, TelegramNotificationBot telegramNotificationBot, UserServiceClient userServiceClient) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.telegramNotificationBot = telegramNotificationBot;
        this.userServiceClient = userServiceClient;
    }

    public List<Notification> getNotificationsByStatus(Notification.NotificationStatus status) {
        return notificationRepository.findByStatus(status);
    }

    public Notification sendNotificationByEvent(SendNotificationEventRequest eventRequest) {
        //Найти пользователя по userId
        User user = userRepository.findById(eventRequest.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + eventRequest.getUserId()));

        //Получить текст сообщения: либо из event, либо из message
        String messageText;
        if (eventRequest.getMessage() != null && !eventRequest.getMessage().isBlank()) {
            messageText = eventRequest.getMessage();
        } else if (eventRequest.getEvent() != null && !eventRequest.getEvent().isBlank()) {
            messageText = getMessageForEvent(eventRequest.getEvent());
        } else {
            throw new IllegalArgumentException("Either event or message must be provided");
        }

        Notification lastSentNotification = null;

        //Проверить настройки уведомлений пользователя и отправить
        if (user.isEnableEmailNotifications()) {
            String email = userServiceClient.getEmailByUserId(eventRequest.getUserId());
            if (email != null && !email.isEmpty()) {
                Notification emailNotification = new Notification(
                    null, // id
                    user.getId(), // userId
                    null, // telegramChatId
                    email, // email
                    messageText,
                    Notification.NotificationType.EMAIL,
                    Notification.NotificationStatus.PENDING,
                    LocalDateTime.now(),
                    user.isEnableEmailNotifications(),
                    user.isEnableTelegramNotifications()
                );
                lastSentNotification = notificationService.sendNotification(emailNotification);
                notificationRepository.save(lastSentNotification);
            }
        }

        if (user.isEnableTelegramNotifications() && user.getTelegramId() != null && !user.getTelegramId().isEmpty()) {
            Notification telegramNotification = new Notification(
                null, // id
                user.getId(), // userId
                user.getTelegramId(), // telegramChatId
                user.getEmail(), // email
                messageText,
                Notification.NotificationType.TELEGRAM,
                Notification.NotificationStatus.PENDING,
                LocalDateTime.now(),
                user.isEnableEmailNotifications(),
                user.isEnableTelegramNotifications()
            );
            lastSentNotification = notificationService.sendNotification(telegramNotification);
            notificationRepository.save(lastSentNotification);
            telegramNotificationBot.sendNotification(user.getTelegramId(), messageText);
        }

        if (lastSentNotification == null) {
            throw new IllegalStateException("No notifications sent for user " + eventRequest.getUserId() + ". Check user preferences.");
        }

        return lastSentNotification;
    }

    private String getMessageForEvent(String event) {
        return switch (event) {
            case "successfulMatch" -> "Успешный матч!";
            case "profileViewed" -> "Ваш профиль просмотрен.";
            default -> throw new IllegalArgumentException("Unknown event: " + event);
        };
    }
}
