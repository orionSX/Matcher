package com.akiora.lolparser;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableRabbit
@EnableConfigurationProperties
public class LolparserApplication {

    public static void main(String[] args) {
        SpringApplication.run(LolparserApplication.class, args);
    }

}
