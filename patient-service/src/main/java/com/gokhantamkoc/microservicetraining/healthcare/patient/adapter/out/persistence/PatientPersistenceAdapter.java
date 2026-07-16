package com.gokhantamkoc.microservicetraining.healthcare.patient.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.patient.domain.Patient;

@Component
public class PatientPersistenceAdapter {
    private final PatientRepository repository;

    public PatientPersistenceAdapter(PatientRepository repository) {
        this.repository = repository;
    }

    public Patient save(Patient patient) {
        PatientEntity entity = new PatientEntity();
        entity.tenantId = patient.tenantId();
        entity.fullName = patient.fullName();
        entity.medicalRecordNumber = patient.medicalRecordNumber();
        entity.phoneNumber = patient.phoneNumber();
        return toDomain(repository.save(entity));
    }

    public Optional<Patient> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    public List<Patient> findByIdIn(List<Long> ids) {
        return repository.findByIdIn(ids).stream().map(this::toDomain).toList();
    }

    public List<Patient> findByTenantId(Long tenantId) {
        return repository.findByTenantId(tenantId).stream().map(this::toDomain).toList();
    }

    private Patient toDomain(PatientEntity entity) {
        return new Patient(entity.id, entity.tenantId, entity.fullName, entity.medicalRecordNumber, entity.phoneNumber);
    }
}

