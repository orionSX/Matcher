package com.example.notifier.domain.adapter;

import com.example.notifier.domain.Notification;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataNotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByStatus(Notification.NotificationStatus status);
    List<Notification> findByTypeAndStatus(Notification.NotificationType type, Notification.NotificationStatus status);
    long countByStatus(Notification.NotificationStatus status);
}