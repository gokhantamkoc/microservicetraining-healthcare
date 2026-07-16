package com.gokhantamkoc.microservicetraining.healthcare.search.domain;

import java.time.Instant;

public record PatientHistoryIndex(Long id, Long tenantId, Long patientId, String summary, Instant occurredAt) {
}

