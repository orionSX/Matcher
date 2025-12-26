package com.example.notifier.app.listener;

import com.example.notifier.domain.User;
import com.example.notifier.domain.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Component
public class NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);
    
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public NotificationListener(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "notification.queue")
    public void handleNotificationMessage(String message) {
        try {
            logger.info("Received notification message: {}", message);
            
            Map<String, Object> data = objectMapper.readValue(message, Map.class);
            String action = (String) data.get("action");
            
            if ("create".equals(action)) {
                handleCreateUser(data);
            } else if ("telegram".equals(action)) {
                handleTelegramUpdate(data);
            } else if ("telegram-disable".equals(action)) {
                handleTelegramDisable(data);
            } else if ("email".equals(action)) {
                handleEmailEnable(data);
            } else if ("email-disable".equals(action)) {
                handleEmailDisable(data);
            } else {
                logger.warn("Unknown action: {}", action);
            }
        } catch (Exception e) {
            logger.error("Error processing notification message: {}", message, e);
        }
    }

    private void handleCreateUser(Map<String, Object> data) {
        String oid = (String) data.get("oid");
        String nickname = (String) data.get("nickname");
        
        logger.info("Creating user in notification service. Oid: {}, Nickname: {}", oid, nickname);
        
        User user = new User();
        user.setId(oid);
        user.setUsername(nickname);
        userRepository.save(user);
    }

    private void handleTelegramUpdate(Map<String, Object> data) {
        String userId = (String) data.get("userId");
        String telegramChatId = (String) data.get("value");
        
        logger.info("Updating Telegram for user: {}", userId);
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        user.setTelegramId(telegramChatId);
        user.setEnableTelegramNotifications(true);
        userRepository.save(user);
    }

    private void handleTelegramDisable(Map<String, Object> data) {
        String userId = (String) data.get("userId");
        
        logger.info("Disabling Telegram for user: {}", userId);
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        user.setEnableTelegramNotifications(false);
        userRepository.save(user);
    }

    private void handleEmailEnable(Map<String, Object> data) {
        String userId = (String) data.get("userId");
        
        logger.info("Enabling Email for user: {}", userId);
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        user.setEnableEmailNotifications(true);
        userRepository.save(user);
    }

    private void handleEmailDisable(Map<String, Object> data) {
        String userId = (String) data.get("userId");
        
        logger.info("Disabling Email for user: {}", userId);
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        user.setEnableEmailNotifications(false);
        userRepository.save(user);
    }
}

