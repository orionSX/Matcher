package com.example.notifier.domain;

import com.example.notifier.domain.Notification;

public interface NotificationService {
    Notification sendNotification(Notification notification);
}
