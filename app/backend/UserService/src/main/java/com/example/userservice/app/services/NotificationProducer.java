package com.example.userservice.app.services;

import com.example.userservice.app.dtos.notification.CreateUserNotificationDTO;
import com.example.userservice.app.dtos.notification.UpdateUserNotificationDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NotificationProducer {
    
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    
    public NotificationProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }
    
    public void sendCreateUserNotification(CreateUserNotificationDTO dto) {
        String message = convertToJson(dto);
        rabbitTemplate.convertAndSend("notification.exchange", "notification.user.create", message);
    }
    
    public void sendUpdateUserNotification(UpdateUserNotificationDTO dto) {
        String message = convertToJson(dto);
        rabbitTemplate.convertAndSend("notification.exchange", "notification.user.update", message);
    }
    
    private String convertToJson(Object dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert DTO to JSON", e);
        }
    }
}
