package com.gokhantamkoc.microservicetraining.healthcare.tenant.domain;

public record AppointmentOperationStatus(String operationId, String status, Appointment appointment, String errorMessage) {
}

