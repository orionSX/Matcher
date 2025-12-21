package com.akiora.searchformservice.Config;


import org.springframework.amqp.core.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
public class RabbitMQConfig {

    @Autowired
    private RabbitMQConfigProperties properties;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(properties.getExchange());
    }

    @Bean
    public Declarables declarables() {
        List<Declarable> declarables = new ArrayList<>();

        TopicExchange exchange = new TopicExchange(properties.getExchange());
        declarables.add(exchange);

        for (RabbitMQConfigProperties.BindingProperties props : properties.getBindings()) {
            Queue queue = new Queue(props.getQueue(), props.isDurable());
            Binding binding = BindingBuilder.bind(queue)
                    .to(exchange)
                    .with(props.getRoutingKey());

            declarables.add(queue);
            declarables.add(binding);
        }

        return new Declarables(declarables);
    }
}