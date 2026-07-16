package com.gokhantamkoc.microservicetraining.healthcare.checkout.adapter.out.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationCommandPublisher {
    public static final String EXCHANGE = "healthcare.notifications";

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public NotificationCommandPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishPaymentReceipt(Long tenantId, Long patientId, String message) {
        try {
            rabbitTemplate.convertAndSend(EXCHANGE, "notification.requested",
                    objectMapper.writeValueAsString(new NotificationCommand(tenantId, patientId, message)));
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Could not serialize notification command", exception);
        }
    }

    record NotificationCommand(Long tenantId, Long patientId, String message) {
    }
}
