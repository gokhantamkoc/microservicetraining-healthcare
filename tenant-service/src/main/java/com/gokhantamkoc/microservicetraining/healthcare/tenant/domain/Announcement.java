package com.gokhantamkoc.microservicetraining.healthcare.tenant.domain;

import java.time.Instant;

public record Announcement(Long id, Long tenantId, String title, String body, Instant publishedAt) {
}

