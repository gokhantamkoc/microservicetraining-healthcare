package com.gokhantamkoc.microservicetraining.healthcare.physician.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.physician.domain.Physician;

@Component
public class PhysicianPersistenceAdapter {
    private final PhysicianRepository repository;

    public PhysicianPersistenceAdapter(PhysicianRepository repository) {
        this.repository = repository;
    }

    public Physician save(Physician physician) {
        PhysicianEntity entity = new PhysicianEntity();
        entity.tenantId = physician.tenantId();
        entity.fullName = physician.fullName();
        entity.specialty = physician.specialty();
        entity.active = physician.active();
        return toDomain(repository.save(entity));
    }

    public List<Physician> findByTenantId(Long tenantId) {
        return repository.findByTenantId(tenantId).stream().map(this::toDomain).toList();
    }

    public Optional<Physician> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    private Physician toDomain(PhysicianEntity entity) {
        return new Physician(entity.id, entity.tenantId, entity.fullName, entity.specialty, entity.active);
    }
}
