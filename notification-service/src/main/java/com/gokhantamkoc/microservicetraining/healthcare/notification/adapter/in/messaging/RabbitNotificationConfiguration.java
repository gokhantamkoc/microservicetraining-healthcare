package com.gokhantamkoc.microservicetraining.healthcare.notification.adapter.in.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RabbitNotificationConfiguration {
    static final String EXCHANGE = "healthcare.notifications";
    static final String REQUEST_QUEUE = "notification.requested.v2";
    static final String AWS_QUEUE = "notification.aws-sns.v2";
    static final String TWILIO_QUEUE = "notification.twilio.v2";
    static final String FCM_QUEUE = "notification.fcm.v2";

    @Bean
    TopicExchange notificationExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Queue notificationRequestQueue() {
        return new Queue(REQUEST_QUEUE);
    }

    @Bean
    Queue awsQueue() {
        return new Queue(AWS_QUEUE);
    }

    @Bean
    Queue twilioQueue() {
        return new Queue(TWILIO_QUEUE);
    }

    @Bean
    Queue fcmQueue() {
        return new Queue(FCM_QUEUE);
    }

    @Bean
    Binding requestBinding(TopicExchange notificationExchange, Queue notificationRequestQueue) {
        return BindingBuilder.bind(notificationRequestQueue).to(notificationExchange).with("notification.requested");
    }

    @Bean
    Binding awsBinding(TopicExchange notificationExchange, Queue awsQueue) {
        return BindingBuilder.bind(awsQueue).to(notificationExchange).with("notification.aws-sns");
    }

    @Bean
    Binding twilioBinding(TopicExchange notificationExchange, Queue twilioQueue) {
        return BindingBuilder.bind(twilioQueue).to(notificationExchange).with("notification.twilio");
    }

    @Bean
    Binding fcmBinding(TopicExchange notificationExchange, Queue fcmQueue) {
        return BindingBuilder.bind(fcmQueue).to(notificationExchange).with("notification.fcm");
    }
}
