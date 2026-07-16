package com.gokhantamkoc.microservicetraining.healthcare.notification.adapter.out.persistence;

import java.time.Instant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationDelivery;
import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationProvider;

@Component
class NotificationMongoSeeder implements CommandLineRunner {
    private final NotificationPersistenceAdapter persistence;

    NotificationMongoSeeder(NotificationPersistenceAdapter persistence) {
        this.persistence = persistence;
    }

    @Override
    public void run(String... args) {
        if (!persistence.findByTenantId(1L).isEmpty()) {
            return;
        }
        persistence.save(new NotificationDelivery(1L, 1L, 1L, 1L, NotificationProvider.AWS_SNS,
                "Seed delivery: appointment reminder for Grace Hopper with Dr. Ada Lovelace",
                Instant.parse("2026-06-01T09:00:00Z")));
        persistence.save(new NotificationDelivery(2L, 2L, 3L, 3L, NotificationProvider.TWILIO,
                "Seed delivery: lab result notification for Tenant B patient",
                Instant.parse("2026-06-02T10:00:00Z")));
        persistence.save(new NotificationDelivery(3L, 3L, 4L, 4L, NotificationProvider.FCM,
                "Seed delivery: vaccination reminder for Tenant C patient",
                Instant.parse("2026-06-03T11:00:00Z")));
    }
}

