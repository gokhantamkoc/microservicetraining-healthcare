package com.gokhantamkoc.microservicetraining.healthcare.notification.adapter.in.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.notification.application.NotificationUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationProvider;

@Component
class NotificationMessageListener {
    private final NotificationUseCase notificationUseCase;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    NotificationMessageListener(NotificationUseCase notificationUseCase, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.notificationUseCase = notificationUseCase;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitNotificationConfiguration.REQUEST_QUEUE)
    void routeByTenantPreference(String payload) {
        NotificationCommand command = read(payload);
        Long tenantId = command.tenantId();
        NotificationProvider provider = notificationUseCase.providerForTenant(tenantId);
        rabbitTemplate.convertAndSend(RabbitNotificationConfiguration.EXCHANGE, provider.routingKey(), payload);
    }

    @RabbitListener(queues = RabbitNotificationConfiguration.AWS_QUEUE)
    void deliverAws(String payload) {
        deliver(payload, NotificationProvider.AWS_SNS);
    }

    @RabbitListener(queues = RabbitNotificationConfiguration.TWILIO_QUEUE)
    void deliverTwilio(String payload) {
        deliver(payload, NotificationProvider.TWILIO);
    }

    @RabbitListener(queues = RabbitNotificationConfiguration.FCM_QUEUE)
    void deliverFcm(String payload) {
        deliver(payload, NotificationProvider.FCM);
    }

    private void deliver(String payload, NotificationProvider provider) {
        NotificationCommand command = read(payload);
        notificationUseCase.deliver(command.tenantId(), command.patientId(), provider, command.message());
    }

    private NotificationCommand read(String payload) {
        try {
            return objectMapper.readValue(payload, NotificationCommand.class);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Invalid notification command payload", exception);
        }
    }

    record NotificationCommand(Long tenantId, Long patientId, String message) {
    }
}
