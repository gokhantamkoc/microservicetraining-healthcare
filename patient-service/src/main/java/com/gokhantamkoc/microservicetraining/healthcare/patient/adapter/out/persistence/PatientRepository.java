package com.gokhantamkoc.microservicetraining.healthcare.patient.adapter.out.persistence;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    List<PatientEntity> findByTenantId(Long tenantId);
    List<PatientEntity> findByIdIn(Collection<Long> ids);
}

