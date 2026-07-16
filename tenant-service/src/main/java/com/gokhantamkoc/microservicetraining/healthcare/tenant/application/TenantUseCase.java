package com.gokhantamkoc.microservicetraining.healthcare.tenant.application;

import java.util.List;
import java.util.Optional;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.NotificationProvider;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Tenant;

public interface TenantUseCase {
    Tenant createTenant(String name, String location, NotificationProvider provider);
    List<Tenant> listTenants();
    Optional<Tenant> findTenant(Long id);
    NotificationProvider notificationProviderFor(Long tenantId);
}

