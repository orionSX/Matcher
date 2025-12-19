
package com.example.notifier.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class UserServiceRestClient {
    @Value("${user.service.url:http://user-service-java:8080}")
    private String userServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, Object>> getAllUsers() {
        String url = userServiceUrl + "/api/users";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        return response.getBody();
    }

    public void createUserInNotifier(Map<String, Object> user) {
        String url = "http://notifier-service:8080/api/notifier-users";
        restTemplate.postForEntity(url, user, Void.class);
    }

    public void updateTelegramChatId(String userId, String telegramChatId) {
        String url = "http://notifier-service:8080/api/notifier-users/" + userId + "/telegram";
        Map<String, String> body = new HashMap<>();
        body.put("telegramChatId", telegramChatId);
        restTemplate.put(url, body);
    }
    public void disableTelegramNotifications(String userId) {
        String url = "http://notifier-service:8080/api/notifier-users/" + userId + "/telegram-disable";
        restTemplate.put(url, null);
    }
}
