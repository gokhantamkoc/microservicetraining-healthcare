package com.gokhantamkoc.microservicetraining.healthcare.physician.domain;

public record Physician(Long id, Long tenantId, String fullName, String specialty, boolean active) {
}

