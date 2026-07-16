package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.in.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.application.TenantUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.NotificationProvider;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Tenant;

@RestController
class TenantRestController {
    private final TenantUseCase tenantUseCase;

    TenantRestController(TenantUseCase tenantUseCase) {
        this.tenantUseCase = tenantUseCase;
    }

    @PostMapping("/tenants")
    Tenant create(@RequestBody CreateTenantRequest request) {
        return tenantUseCase.createTenant(request.name(), request.location(), request.notificationProvider());
    }

    @GetMapping("/tenants")
    List<Tenant> list() {
        return tenantUseCase.listTenants();
    }

    @GetMapping("/tenants/{id}/notification-provider")
    NotificationProvider notificationProvider(@PathVariable Long id) {
        return tenantUseCase.notificationProviderFor(id);
    }

    record CreateTenantRequest(String name, String location, NotificationProvider notificationProvider) {
    }
}

