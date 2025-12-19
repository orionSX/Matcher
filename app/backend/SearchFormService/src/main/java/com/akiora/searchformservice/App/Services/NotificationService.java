package com.akiora.searchformservice.App.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class NotificationService {

    private final WebClient webClient;

    public NotificationService(
            @Value("${notificator.url:https://notificator:5000}") String notificatorUrl
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(notificatorUrl)
                .build();
    }

    public void sendMatchNotification(String userId1, String userId2, String formId) {
        Map<String, String> payload = new HashMap<>();
        payload.put("message", "произошел мэтч");
        payload.put("userId1", userId1);
        payload.put("userId2", userId2);
        payload.put("formId", formId);

        webClient.post()
                .uri("/notify")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("Match notification sent successfully: {}", response))
                .doOnError(error -> log.error("Failed to send match notification", error))
                .onErrorResume(e -> Mono.empty())
                .subscribe();
    }
}
