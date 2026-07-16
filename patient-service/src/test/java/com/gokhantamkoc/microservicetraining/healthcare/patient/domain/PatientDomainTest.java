package com.gokhantamkoc.microservicetraining.healthcare.patient.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class PatientDomainTest {
    @Test
    void patientKeepsIdentificationAndContactData() {
        Patient patient = new Patient(1L, 10L, "Grace Hopper", "MRN-1001", "+905551112233");

        assertThat(patient.id()).isEqualTo(1L);
        assertThat(patient.tenantId()).isEqualTo(10L);
        assertThat(patient.fullName()).isEqualTo("Grace Hopper");
        assertThat(patient.medicalRecordNumber()).isEqualTo("MRN-1001");
        assertThat(patient.phoneNumber()).isEqualTo("+905551112233");
    }

    @Test
    void medicalHistoryEntryKeepsPatientHistoryDetails() {
        Instant occurredAt = Instant.parse("2026-01-10T09:30:00Z");

        MedicalHistoryEntry entry = new MedicalHistoryEntry(1L, 2L, "Annual cardiology checkup", occurredAt);

        assertThat(entry.id()).isEqualTo(1L);
        assertThat(entry.patientId()).isEqualTo(2L);
        assertThat(entry.summary()).isEqualTo("Annual cardiology checkup");
        assertThat(entry.occurredAt()).isEqualTo(occurredAt);
    }
}

