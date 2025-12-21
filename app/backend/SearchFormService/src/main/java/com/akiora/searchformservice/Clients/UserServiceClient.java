package com.akiora.searchformservice.Clients;


import com.akiora.searchformservice.Shared.NotifyEvent;
import com.akiora.searchformservice.Shared.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceClient {
    @Value("${user.service.url}")
    private String baseUrl;
    private final WebClient webClient;

    public UserServiceClient(
           
    ) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl+"/api/users")
                .build();
    }

    public NotifyEvent sendNotificationData(String receiverId, String senderId) {
        // Параллельные запросы
        Mono<UserDTO> senderMono = getUserData(senderId);
                

        Mono<UserDTO> receiverMono = getUserData(receiverId);
              

        
        Tuple2<UserDTO, UserDTO> users = Mono.zip(senderMono, receiverMono)
                .blockOptional()
                .orElseThrow(() -> new RuntimeException("Failed to fetch user data"));
    
        
        String message= "User "+users.getT2().getNickname() +" liked your form!";

 
        
        
        return NotifyEvent.builder().user_id(receiverId).message(message).build();
    }

    private Mono<UserDTO> getUserData(String userId) {
        return webClient.get()
                .uri("/{id}", userId)
                .retrieve()
                .bodyToMono(UserDTO.class);
    }

    
}
