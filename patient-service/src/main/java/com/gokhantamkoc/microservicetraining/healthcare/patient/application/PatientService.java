package com.gokhantamkoc.microservicetraining.healthcare.patient.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokhantamkoc.microservicetraining.healthcare.patient.adapter.out.persistence.PatientPersistenceAdapter;
import com.gokhantamkoc.microservicetraining.healthcare.patient.domain.Patient;

@Service
@Transactional
public class PatientService implements PatientUseCase {
    private final PatientPersistenceAdapter persistence;

    public PatientService(PatientPersistenceAdapter persistence) {
        this.persistence = persistence;
    }

    @Override
    public Patient create(Long tenantId, String fullName, String medicalRecordNumber, String phoneNumber) {
        return persistence.save(new Patient(null, tenantId, fullName, medicalRecordNumber, phoneNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> findById(Long id) {
        return persistence.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Patient> findByIds(List<Long> ids) {
        return persistence.findByIdIn(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Patient> listByTenant(Long tenantId) {
        return persistence.findByTenantId(tenantId);
    }
}

