package com.akiora.formconsumer;

import com.akiora.formconsumer.Shared.NotifyEvent;
import com.akiora.formconsumer.Shared.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceClient {
    @Value("${user.service.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    public UserServiceClient() {
        this.restTemplate = new RestTemplate();
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl + "/api/users")
                .build();
    }

   
    public NotifyEvent getNotificationEvent(String receiverId, String senderId) {
        System.out.println("Receiver ID: " + receiverId + ", Sender ID: " + senderId);

        try {
            
            UserDTO sender = getUserDataSync(senderId);
            UserDTO receiver = getUserDataSync(receiverId);
            StringBuilder st= new StringBuilder("email: "+sender.getEmail());
            st.append(", ");
            if (sender.getAge()!=null && sender.getAge()>=12) {
                st.append("age: ");
                st.append(sender.getAge().toString());
                st.append(" ");
            }
            List<String> socialsList=new ArrayList<>();
            if (sender.getSocials() != null) {
                socialsList = sender.getSocials().entrySet().stream()
                        .map(entry -> entry.getKey() + ": " + entry.getValue().url()+" ")
                        .filter(s -> !s.toLowerCase().contains("additional"))
                        .collect(Collectors.toList());
            }
            st.append("socials: ");
            if(socialsList.isEmpty()) {
                st.append("none ");
            }
            for (String social : socialsList) {
                if(!social.toLowerCase().contains("additional")) {
                    
                    st.append(social);
                    
                }
                
            
            }
            String message = "User " + sender.getNickname()+"( "+st+")" + " liked your form!";

            return NotifyEvent.builder()
                    .user_id(receiverId)
                    .message(message)
                    .build();

        } catch (Exception e) {
            System.err.println("Error getting notification event: " + e.getMessage());
            e.printStackTrace();

            // Fallback в случае ошибки
            String message = "Someone liked your form!";
            return NotifyEvent.builder()
                    .user_id(receiverId)
                    .message(message)
                    .build();
        }
    }

    // Синхронный метод с RestTemplate
    private UserDTO getUserDataSync(String userId) {
        String url = baseUrl + "/api/users/" + userId;
        try {
            return restTemplate.getForObject(url, UserDTO.class);
        } catch (Exception e) {
            System.err.println("Error fetching user " + userId + ": " + e.getMessage());
            // Возвращаем заглушку в случае ошибки
            return UserDTO.builder()
                    .oid(UUID.fromString(userId))
                    .nickname("User")
                    .build();
        }
    }

    // Альтернативный вариант с WebClient и блокировкой
    private UserDTO getUserDataWebClientSync(String userId) {
        try {
            return webClient.get()
                    .uri("/{id}", userId)
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .timeout(java.time.Duration.ofSeconds(5))
                    .block();
        } catch (Exception e) {
            System.err.println("Error fetching user " + userId + " via WebClient: " + e.getMessage());
            return UserDTO.builder()
                    .oid(UUID.fromString(userId))
                    .nickname("User")
                    .build();
        }
    }
}