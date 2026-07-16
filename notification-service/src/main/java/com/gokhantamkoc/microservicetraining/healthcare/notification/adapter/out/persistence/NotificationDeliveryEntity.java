package com.gokhantamkoc.microservicetraining.healthcare.notification.adapter.out.persistence;

import java.time.Instant;

import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationProvider;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("notification_deliveries")
class NotificationDeliveryEntity {
    @Id
    Long id;
    Long tenantId;
    Long patientId;
    Long physicianId;
    NotificationProvider provider;
    String message;
    Instant deliveredAt;
}
