package com.akiora.searchformservice.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQConfigProperties {

    private String exchange;
    private List<BindingProperties> bindings = new ArrayList<>();

  

    @Data
    public static class BindingProperties {
        private String queue;
        private String routingKey;
        private boolean durable = true;

  
    }
}
