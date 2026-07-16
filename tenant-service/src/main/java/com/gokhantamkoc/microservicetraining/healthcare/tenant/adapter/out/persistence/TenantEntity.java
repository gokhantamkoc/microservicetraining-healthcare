package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.NotificationProvider;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tenants")
class TenantEntity {
    @Id
    Long id;
    String name;
    String location;
    NotificationProvider notificationProvider;
}
