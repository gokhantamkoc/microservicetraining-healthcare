package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.in.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.application.TenantUseCase;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.NotificationProvider;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Tenant;

@Controller
class TenantGraphqlController {
    private final TenantUseCase tenantUseCase;

    TenantGraphqlController(TenantUseCase tenantUseCase) {
        this.tenantUseCase = tenantUseCase;
    }

    @QueryMapping
    List<Tenant> tenants() {
        return tenantUseCase.listTenants();
    }

    @MutationMapping
    Tenant createTenant(@Argument String name, @Argument String location, @Argument NotificationProvider provider) {
        return tenantUseCase.createTenant(name, location, provider);
    }
}

