package com.gokhantamkoc.microservicetraining.healthcare.tenant.domain;

public record Tenant(Long id, String name, String location, NotificationProvider notificationProvider) {
}

