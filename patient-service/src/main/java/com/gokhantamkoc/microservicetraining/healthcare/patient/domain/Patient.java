package com.gokhantamkoc.microservicetraining.healthcare.patient.domain;

public record Patient(Long id, Long tenantId, String fullName, String medicalRecordNumber, String phoneNumber) {
}

