package com.gokhantamkoc.microservicetraining.healthcare.physician.application;

import java.util.List;
import java.util.Optional;

import com.gokhantamkoc.microservicetraining.healthcare.physician.domain.Physician;

public interface PhysicianUseCase {
    Physician create(Long tenantId, String fullName, String specialty);
    Optional<Physician> findById(Long id);
    List<Physician> listByTenant(Long tenantId);
}
