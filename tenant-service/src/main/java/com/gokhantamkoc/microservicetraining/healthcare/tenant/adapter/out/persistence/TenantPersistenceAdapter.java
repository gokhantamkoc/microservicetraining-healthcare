package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.tenant.domain.Tenant;

@Component
public class TenantPersistenceAdapter {
    private final TenantRepository repository;

    public TenantPersistenceAdapter(TenantRepository repository) {
        this.repository = repository;
    }

    public Tenant save(Tenant tenant) {
        TenantEntity entity = new TenantEntity();
        entity.id = tenant.id() == null ? nextId() : tenant.id();
        entity.name = tenant.name();
        entity.location = tenant.location();
        entity.notificationProvider = tenant.notificationProvider();
        return toDomain(repository.save(entity));
    }

    public List<Tenant> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    public Optional<Tenant> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    private Tenant toDomain(TenantEntity entity) {
        return new Tenant(entity.id, entity.name, entity.location, entity.notificationProvider);
    }

    private Long nextId() {
        return repository.findTopByOrderByIdDesc().map(entity -> entity.id + 1).orElse(1L);
    }
}
