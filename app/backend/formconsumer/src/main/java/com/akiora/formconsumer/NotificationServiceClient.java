package com.akiora.formconsumer;

import com.akiora.formconsumer.Shared.NotifyEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationServiceClient {
    @Value("${notification.service.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public NotificationServiceClient() {
        this.restTemplate = new RestTemplate();
    }

    public String notifyUser(NotifyEvent notifyEvent) {
        if (notifyEvent == null) {
            System.out.println("notifyEvent is null");
            return "Invalid NotifyEvent";
        }

        if (notifyEvent.getUser_id() == null || notifyEvent.getMessage() == null) {
            System.out.println("notifyEvent data is invalid");
            return "Invalid NotifyEvent data";
        }

        String url = baseUrl + "/api/notifications/by-event";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("userId", notifyEvent.getUser_id());
        requestBody.put("message", notifyEvent.getMessage());

        System.out.println("Sending notification to user: " + notifyEvent.getUser_id());
        System.out.println("URL: " + url);
        System.out.println("Body: " + requestBody);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    url,
                    request,
                    String.class
            );

            System.out.println("Response status: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody());

            return response.getBody() != null ? response.getBody() : "No response body";

        } catch (Exception e) {
            System.err.println("Error during notification request: " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}