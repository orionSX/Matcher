package com.akiora.searchformservice.App.Services;

import com.akiora.searchformservice.Events.FormMatchedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.logging.Logger;

@Service
public class FormEventProducer {
  
    @Value("${spring.rabbitmq.exchange}")
    private String exchangeName;   
    
    ObjectMapper mapper;
    public static final Logger logger = Logger.getLogger(FormEventProducer.class.getName());

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public FormEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.mapper = new ObjectMapper();
      
    }
    
    public void sendFormMatchedEvent(FormMatchedEvent formMatchedEvent) {

      
        String event = mapper.writeValueAsString(formMatchedEvent);
        logger.info("Sending form matched event to Rabbit" + event);
       
        rabbitTemplate.convertAndSend(exchangeName,"form.matched", event);
        rabbitTemplate.convertAndSend(exchangeName,"log.form.matched", event);
     
    }
    public void sendMessage(String message) {
      
        rabbitTemplate.convertAndSend(exchangeName, "form.created.queue", message);
    }
    
    
    
    
}
