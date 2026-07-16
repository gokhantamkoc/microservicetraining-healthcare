package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence;

import java.time.Instant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Announcement;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Appointment;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.NotificationProvider;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Tenant;

@Component
class TenantMongoSeeder implements CommandLineRunner {
    private final TenantPersistenceAdapter tenantPersistence;
    private final AppointmentPersistenceAdapter appointmentPersistence;
    private final AnnouncementPersistenceAdapter announcementPersistence;

    TenantMongoSeeder(
            TenantPersistenceAdapter tenantPersistence,
            AppointmentPersistenceAdapter appointmentPersistence,
            AnnouncementPersistenceAdapter announcementPersistence) {
        this.tenantPersistence = tenantPersistence;
        this.appointmentPersistence = appointmentPersistence;
        this.announcementPersistence = announcementPersistence;
    }

    @Override
    public void run(String... args) {
        if (tenantPersistence.findAll().isEmpty()) {
            tenantPersistence.save(new Tenant(1L, "Tenant A Clinic", "Istanbul", NotificationProvider.AWS_SNS));
            tenantPersistence.save(new Tenant(2L, "Tenant B Family Health", "Ankara", NotificationProvider.TWILIO));
            tenantPersistence.save(new Tenant(3L, "Tenant C Pediatric Center", "Izmir", NotificationProvider.FCM));
        }
        if (appointmentPersistence.findByTenantId(1L).isEmpty()) {
            appointmentPersistence.save(new Appointment(1L, 1L, 1L, 1L,
                    "Grace Hopper", "Dr. Ada Lovelace", "Cardiology",
                    Instant.parse("2026-07-20T09:00:00Z"), "SCHEDULED"));
            appointmentPersistence.save(new Appointment(2L, 1L, 2L, 2L,
                    "Margaret Hamilton", "Dr. Alan Turing", "Neurology",
                    Instant.parse("2026-07-21T11:30:00Z"), "SCHEDULED"));
        }
        if (announcementPersistence.findByTenantId(1L).isEmpty()) {
            announcementPersistence.save(new Announcement(1L, 1L, "Holiday Hours",
                    "Tenant A Clinic will close at 16:00 on Friday.", Instant.parse("2026-07-01T08:00:00Z")));
            announcementPersistence.save(new Announcement(2L, 2L, "New Family Medicine Schedule",
                    "Tenant B added evening family medicine appointments.", Instant.parse("2026-07-02T08:00:00Z")));
            announcementPersistence.save(new Announcement(3L, 3L, "Vaccination Reminder",
                    "Tenant C pediatric vaccination reminders are active.", Instant.parse("2026-07-03T08:00:00Z")));
        }
    }
}

