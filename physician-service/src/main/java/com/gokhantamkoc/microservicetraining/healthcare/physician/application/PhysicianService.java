package com.gokhantamkoc.microservicetraining.healthcare.physician.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokhantamkoc.microservicetraining.healthcare.physician.adapter.out.persistence.PhysicianPersistenceAdapter;
import com.gokhantamkoc.microservicetraining.healthcare.physician.domain.Physician;

@Service
@Transactional
public class PhysicianService implements PhysicianUseCase {
    private final PhysicianPersistenceAdapter persistence;

    public PhysicianService(PhysicianPersistenceAdapter persistence) {
        this.persistence = persistence;
    }

    @Override
    public Physician create(Long tenantId, String fullName, String specialty) {
        return persistence.save(new Physician(null, tenantId, fullName, specialty, true));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Physician> findById(Long id) {
        return persistence.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Physician> listByTenant(Long tenantId) {
        return persistence.findByTenantId(tenantId);
    }
}
