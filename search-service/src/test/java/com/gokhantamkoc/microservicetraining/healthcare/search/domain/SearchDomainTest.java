package com.gokhantamkoc.microservicetraining.healthcare.search.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class SearchDomainTest {
    @Test
    void patientHistoryIndexKeepsSearchableHistoryData() {
        Instant occurredAt = Instant.parse("2026-02-15T11:00:00Z");

        PatientHistoryIndex index = new PatientHistoryIndex(1L, 2L, 3L, "Follow-up lab tests requested", occurredAt);

        assertThat(index.id()).isEqualTo(1L);
        assertThat(index.tenantId()).isEqualTo(2L);
        assertThat(index.patientId()).isEqualTo(3L);
        assertThat(index.summary()).isEqualTo("Follow-up lab tests requested");
        assertThat(index.occurredAt()).isEqualTo(occurredAt);
    }
}

