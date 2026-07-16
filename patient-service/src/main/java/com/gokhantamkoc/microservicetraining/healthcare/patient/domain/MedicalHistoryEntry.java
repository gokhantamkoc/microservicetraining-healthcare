package com.gokhantamkoc.microservicetraining.healthcare.patient.domain;

import java.time.Instant;

public record MedicalHistoryEntry(Long id, Long patientId, String summary, Instant occurredAt) {
}

