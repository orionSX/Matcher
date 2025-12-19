package com.example.userservice.app.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotifierRestClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String notifierUrl = "http://notification-service:5003/api/notifier-users";

    public void createUserInNotifier(String oid, String nickname) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("oid", oid);
        userData.put("nickname", nickname);
        restTemplate.postForEntity(notifierUrl, userData, Void.class);
    }
}
