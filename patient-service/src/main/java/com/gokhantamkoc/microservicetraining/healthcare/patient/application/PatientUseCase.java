package com.gokhantamkoc.microservicetraining.healthcare.patient.application;

import java.util.List;
import java.util.Optional;

import com.gokhantamkoc.microservicetraining.healthcare.patient.domain.Patient;

public interface PatientUseCase {
    Patient create(Long tenantId, String fullName, String medicalRecordNumber, String phoneNumber);
    Optional<Patient> findById(Long id);
    List<Patient> findByIds(List<Long> ids);
    List<Patient> listByTenant(Long tenantId);
}

