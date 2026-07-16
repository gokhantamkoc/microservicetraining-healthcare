package com.gokhantamkoc.microservicetraining.healthcare.notification.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class NotificationDomainTest {
    @Test
    void notificationProvidersExposeRabbitRoutingKeys() {
        assertThat(NotificationProvider.AWS_SNS.routingKey()).isEqualTo("notification.aws-sns");
        assertThat(NotificationProvider.TWILIO.routingKey()).isEqualTo("notification.twilio");
        assertThat(NotificationProvider.FCM.routingKey()).isEqualTo("notification.fcm");
    }

    @Test
    void notificationDeliveryKeepsDeliveryData() {
        Instant deliveredAt = Instant.parse("2026-06-01T09:00:00Z");

        NotificationDelivery delivery = new NotificationDelivery(
                1L,
                2L,
                3L,
                4L,
                NotificationProvider.AWS_SNS,
                "Appointment scheduled",
                deliveredAt);

        assertThat(delivery.tenantId()).isEqualTo(2L);
        assertThat(delivery.patientId()).isEqualTo(3L);
        assertThat(delivery.physicianId()).isEqualTo(4L);
        assertThat(delivery.provider()).isEqualTo(NotificationProvider.AWS_SNS);
        assertThat(delivery.message()).isEqualTo("Appointment scheduled");
        assertThat(delivery.deliveredAt()).isEqualTo(deliveredAt);
    }

    @Test
    void notificationOperationStatusCanRepresentFailure() {
        NotificationOperationStatus status = new NotificationOperationStatus("operation-1", "FAILED", null, "Patient not found");

        assertThat(status.operationId()).isEqualTo("operation-1");
        assertThat(status.status()).isEqualTo("FAILED");
        assertThat(status.delivery()).isNull();
        assertThat(status.errorMessage()).isEqualTo("Patient not found");
    }
}

