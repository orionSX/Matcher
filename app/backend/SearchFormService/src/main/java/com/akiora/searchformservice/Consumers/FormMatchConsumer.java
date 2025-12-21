package com.akiora.searchformservice.Consumers;


import com.akiora.searchformservice.App.Services.FormEventProducer;
import com.akiora.searchformservice.Clients.UserServiceClient;
import com.akiora.searchformservice.Shared.FormMatchedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.LoggingCacheErrorHandler;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.logging.Logger;

@Slf4j
@Service
public class FormMatchConsumer {
    public static final Logger logger = Logger.getLogger(FormMatchConsumer.class.getName());
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    UserServiceClient userServiceClient;
    @RabbitListener(queues = "form.matched.queue")
    public void process(String message) {
        logger.info(message);
        try {

            var fme = mapper.readValue(message, FormMatchedEvent.class);
            
            logger.info(fme.toString());
        }
        catch (Exception e) {
            logger.warning(e.getMessage());
        }
        
    }
    
    
}
