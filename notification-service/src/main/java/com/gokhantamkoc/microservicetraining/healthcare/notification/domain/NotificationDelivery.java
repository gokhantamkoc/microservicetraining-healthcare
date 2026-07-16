package com.gokhantamkoc.microservicetraining.healthcare.notification.domain;

import java.time.Instant;

public record NotificationDelivery(Long id, Long tenantId, Long patientId, Long physicianId, NotificationProvider provider, String message, Instant deliveredAt) {
}
