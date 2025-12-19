package com.example.notifier.domain.adapter;

import com.example.notifier.domain.Notification;
import com.example.notifier.domain.NotificationRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.List;

@Component
public class MongoNotificationRepositoryAdapter implements NotificationRepository {

    private final SpringDataNotificationRepository springDataNotificationRepository;

    public MongoNotificationRepositoryAdapter(SpringDataNotificationRepository springDataNotificationRepository) {
        this.springDataNotificationRepository = springDataNotificationRepository;
    }

    @Override
    public Notification save(Notification notification) {
        return springDataNotificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> findById(String id) {
        return springDataNotificationRepository.findById(id);
    }

    @Override
    public List<Notification> findByStatus(Notification.NotificationStatus status) {
        return springDataNotificationRepository.findByStatus(status);
    }
}