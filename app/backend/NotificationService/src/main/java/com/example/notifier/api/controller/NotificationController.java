package com.example.notifier.api.controller;

import com.example.notifier.app.service.SendNotificationService;
import com.example.notifier.domain.Notification;
import com.example.notifier.api.dto.NotificationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import com.example.notifier.app.mapper.NotificationMapper;
import com.example.notifier.app.dto.SendNotificationEventRequest;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final SendNotificationService sendNotificationService;
    private final NotificationMapper notificationMapper;

    public NotificationController(SendNotificationService sendNotificationService, NotificationMapper notificationMapper) {
        this.sendNotificationService = sendNotificationService;
        this.notificationMapper = notificationMapper;
    }

    @PostMapping("/by-event")
    public ResponseEntity<NotificationResponseDTO> sendNotificationByEvent(@RequestBody SendNotificationEventRequest request) {
        Notification notification = sendNotificationService.sendNotificationByEvent(request);
        return ResponseEntity.ok(notificationMapper.toDto(notification));
    }
    

    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByStatus(@PathVariable Notification.NotificationStatus status) {
        List<Notification> notifications = sendNotificationService.getNotificationsByStatus(status);
        List<NotificationResponseDTO> responseDTOs = notificationMapper.toDtoList(notifications);
        return ResponseEntity.ok(responseDTOs);
    }
}
