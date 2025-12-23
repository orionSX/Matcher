package com.akiora.lolparser.services;

import com.akiora.lolparser.events.AccountParseEvent;
import com.akiora.lolparser.models.SummonerStats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Service
@Slf4j
public class AccountParseListener {

    @Autowired
    private OpGgService opGgService;
    
    @Value("${form.service.url}")
    private String formServiceUrl;
    
    private final ObjectMapper objectMapper;
    private final RestClient restClient;

    public AccountParseListener() {
        this.objectMapper = new ObjectMapper();     
        
        
        
        this.restClient = RestClient.builder()      
                .build();
    }

    @RabbitListener(queues = "form.account.queue")
    public void handleAccountParseEvent(String message) {
        try {
            log.info("Received account parse event: {}", message);
            
            AccountParseEvent event = objectMapper.readValue(message, AccountParseEvent.class);
            
            // Parse the account
            log.info("Parsing account: {} - {} ({})", event.getAccountName(), event.getAccountTag(), event.getAccountServer());
            SummonerStats stats = opGgService.getSummonerStats(
                    event.getAccountServer(),
                    event.getAccountName(),
                    event.getAccountTag()
            );
            
            // Log the parsed stats
            String statsJson = objectMapper.writeValueAsString(stats);
            log.info("Parsed stats JSON: {}", statsJson);
            
            // Send the parsed data back to the form service
            String updateUrl = formServiceUrl + "/api/v1/forms/" + event.getFormId() + "/account-info";
            log.info("Sending parsed data to: {}", updateUrl);
            
            restClient.put()
                    .uri(updateUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(stats)
                    .retrieve()
                    .toBodilessEntity();
            
            log.info("Successfully updated form {} with account info", event.getFormId());
            
        } catch (IOException e) {
            log.error("Error parsing account: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error processing account parse event: {}", e.getMessage(), e);
        }
    }
}
