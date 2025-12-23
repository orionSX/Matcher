package com.akiora.formconsumer;

import com.akiora.formconsumer.Shared.UserData;
import com.akiora.formconsumer.Shared.UserDataFetchEvent;
import com.akiora.formconsumer.Shared.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserDataFetchConsumer {
    public static final Logger logger = Logger.getLogger(UserDataFetchConsumer.class.getName());
    
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${user.service.url}")
    private String userServiceUrl;
    
    @Value("${form.service.url}")
    private String searchServiceUrl;
    
    @Autowired
    UserServiceClient userServiceClient;
    
    @RabbitListener(queues = "form.user.queue")
    public void process(String message) {
        logger.info("Received user data fetch event: " + message);
        
        try {
            // Parse event
            var event = mapper.readValue(message, UserDataFetchEvent.class);
            logger.info("Parsed UserDataFetchEvent - FormId: " + event.getFormId() + 
                       " CreatorId: " + event.getCreatorId());
            
            // Fetch user data from UserService
            String userUrl = userServiceUrl + "/api/users/" + event.getCreatorId();
            logger.info("Fetching user data from: " + userUrl);
            
            UserDTO user = restTemplate.getForObject(userUrl, UserDTO.class);
            
            if (user == null) {
                logger.warning("User not found for id: " + event.getCreatorId());
                return;
            }
            
            logger.info("Fetched user data - Nickname: " + user.getNickname() + 
                       ", Email: " + user.getEmail() + ", Age: " + user.getAge());
            
          
            List<String> socialsList = null;
            if (user.getSocials() != null) {
                socialsList = user.getSocials().entrySet().stream()
                        .map(entry -> entry.getKey() + ": " + entry.getValue().url())
                        .collect(Collectors.toList());
            }
            
     
            UserData userData = UserData.builder()
                    .socials(socialsList)
                    .age(user.getAge())
                    .gender(user.getGender())
                    .nickname(user.getNickname())
                    .email(user.getEmail())
                    .build();
            
            logger.info("Fetched user data - UserData: " + userData);
            String formUpdateUrl = searchServiceUrl + "/api/v1/forms/" + event.getFormId() + "/user-data";
            logger.info("Updating form at: " + formUpdateUrl);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("socials", userData.getSocials());
            requestBody.put("age", userData.getAge());
            requestBody.put("gender", userData.getGender().toUpperCase());
            requestBody.put("nickname", userData.getNickname());
            requestBody.put("email", userData.getEmail());
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            restTemplate.put(formUpdateUrl, request);
            
            logger.info("Successfully updated form " + event.getFormId() + " with user data");
            
        } catch (Exception e) {
            logger.severe("Exception during user data fetch: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
