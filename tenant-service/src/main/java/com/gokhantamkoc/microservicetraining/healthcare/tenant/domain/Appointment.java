package com.gokhantamkoc.microservicetraining.healthcare.tenant.domain;

import java.time.Instant;

public record Appointment(
        Long id,
        Long tenantId,
        Long patientId,
        Long physicianId,
        String patientName,
        String physicianName,
        String physicianSpecialty,
        Instant startsAt,
        String status) {
}
