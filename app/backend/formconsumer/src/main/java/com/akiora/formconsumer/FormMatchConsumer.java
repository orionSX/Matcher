package com.akiora.formconsumer;





import com.akiora.formconsumer.Shared.FormMatchedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.util.logging.Logger;

@Slf4j
@Service
public class FormMatchConsumer {
    public static final Logger logger = Logger.getLogger(FormMatchConsumer.class.getName());
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    UserServiceClient userServiceClient;
    @Autowired 
    NotificationServiceClient notificationServiceClient;
    @RabbitListener(queues = "form.matched.queue")
    public void process(String message) {
        logger.info("Received message: " + message);

        try {
          
            var fme = mapper.readValue(message, FormMatchedEvent.class);
            logger.info("Parsed FormMatchedEvent - Creator:" +fme.getFormCreator()+" Liker: "+
                    fme.getLikeSender());

      
            var ne = userServiceClient.getNotificationEvent(fme.getFormCreator(), fme.getLikeSender());
            logger.info("Created NotificationEvent: "+ ne);

       
            String notificationResponse = notificationServiceClient.notifyUser(ne);
            logger.info("Notification service response: " + notificationResponse);

      
        } catch (Exception e) {
            logger.severe("Exception during message processing: "+ e.getMessage());
        }
    }

}