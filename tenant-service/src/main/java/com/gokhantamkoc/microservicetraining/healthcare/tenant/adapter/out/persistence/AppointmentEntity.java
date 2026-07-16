package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("appointments")
class AppointmentEntity {
    @Id
    Long id;
    Long tenantId;
    Long patientId;
    Long physicianId;
    String patientName;
    String physicianName;
    String physicianSpecialty;
    Instant startsAt;
    String status;
}
