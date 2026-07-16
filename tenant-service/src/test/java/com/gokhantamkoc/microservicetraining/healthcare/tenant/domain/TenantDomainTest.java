package com.gokhantamkoc.microservicetraining.healthcare.tenant.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class TenantDomainTest {
    @Test
    void tenantKeepsClinicIdentityAndNotificationProvider() {
        Tenant tenant = new Tenant(1L, "Tenant A Clinic", "Istanbul", NotificationProvider.AWS_SNS);

        assertThat(tenant.id()).isEqualTo(1L);
        assertThat(tenant.name()).isEqualTo("Tenant A Clinic");
        assertThat(tenant.location()).isEqualTo("Istanbul");
        assertThat(tenant.notificationProvider()).isEqualTo(NotificationProvider.AWS_SNS);
    }

    @Test
    void appointmentKeepsEnrichedPatientAndPhysicianData() {
        Instant startsAt = Instant.parse("2026-07-27T09:00:00Z");

        Appointment appointment = new Appointment(
                10L,
                1L,
                2L,
                3L,
                "Grace Hopper",
                "Dr. Ada Lovelace",
                "Cardiology",
                startsAt,
                "SCHEDULED");

        assertThat(appointment.patientName()).isEqualTo("Grace Hopper");
        assertThat(appointment.physicianName()).isEqualTo("Dr. Ada Lovelace");
        assertThat(appointment.physicianSpecialty()).isEqualTo("Cardiology");
        assertThat(appointment.startsAt()).isEqualTo(startsAt);
        assertThat(appointment.status()).isEqualTo("SCHEDULED");
    }

    @Test
    void appointmentOperationStatusCanRepresentCompletedAsyncWork() {
        Appointment appointment = new Appointment(1L, 1L, 1L, 1L, "Patient", "Physician", "Family Medicine", Instant.EPOCH, "SCHEDULED");

        AppointmentOperationStatus status = new AppointmentOperationStatus("operation-1", "COMPLETED", appointment, null);

        assertThat(status.operationId()).isEqualTo("operation-1");
        assertThat(status.status()).isEqualTo("COMPLETED");
        assertThat(status.appointment()).isEqualTo(appointment);
        assertThat(status.errorMessage()).isNull();
    }

    @Test
    void announcementKeepsPublicationDetails() {
        Instant publishedAt = Instant.parse("2026-07-01T08:00:00Z");

        Announcement announcement = new Announcement(1L, 1L, "Holiday Hours", "Closed early Friday.", publishedAt);

        assertThat(announcement.tenantId()).isEqualTo(1L);
        assertThat(announcement.title()).isEqualTo("Holiday Hours");
        assertThat(announcement.body()).isEqualTo("Closed early Friday.");
        assertThat(announcement.publishedAt()).isEqualTo(publishedAt);
    }
}

