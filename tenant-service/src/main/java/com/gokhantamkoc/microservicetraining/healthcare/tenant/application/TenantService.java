package com.gokhantamkoc.microservicetraining.healthcare.tenant.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence.TenantPersistenceAdapter;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.NotificationProvider;
import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Tenant;

@Service
@Transactional
public class TenantService implements TenantUseCase {
    private final TenantPersistenceAdapter tenantPersistence;

    public TenantService(TenantPersistenceAdapter tenantPersistence) {
        this.tenantPersistence = tenantPersistence;
    }

    @Override
    public Tenant createTenant(String name, String location, NotificationProvider provider) {
        return tenantPersistence.save(new Tenant(null, name, location, provider));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tenant> listTenants() {
        return tenantPersistence.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tenant> findTenant(Long id) {
        return tenantPersistence.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationProvider notificationProviderFor(Long tenantId) {
        return findTenant(tenantId).map(Tenant::notificationProvider).orElse(NotificationProvider.AWS_SNS);
    }
}

