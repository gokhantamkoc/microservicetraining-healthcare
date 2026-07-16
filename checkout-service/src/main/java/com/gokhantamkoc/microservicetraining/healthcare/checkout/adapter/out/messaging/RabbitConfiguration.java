package com.gokhantamkoc.microservicetraining.healthcare.checkout.adapter.out.messaging;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RabbitConfiguration {
    @Bean
    TopicExchange notificationExchange() {
        return new TopicExchange(NotificationCommandPublisher.EXCHANGE);
    }
}

