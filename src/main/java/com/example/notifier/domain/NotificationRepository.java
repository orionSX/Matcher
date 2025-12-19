package com.example.notifier.domain;

import com.example.notifier.domain.Notification;
import java.util.List;

import java.util.Optional;

public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> findById(String id);
    List<Notification> findByStatus(Notification.NotificationStatus status);
}
