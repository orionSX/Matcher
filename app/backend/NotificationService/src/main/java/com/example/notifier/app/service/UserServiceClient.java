package com.example.notifier.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class UserServiceClient {
    @Value("${user.service.url:http://user-service-java:8080}")
    private String userServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getEmailByUserId(String userId) {
        String url = userServiceUrl + "/api/users/" + userId + "/email";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
