package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("announcements")
class AnnouncementEntity {
    @Id
    Long id;
    Long tenantId;
    String title;
    String body;
    Instant publishedAt;
}
