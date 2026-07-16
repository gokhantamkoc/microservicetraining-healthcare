package com.gokhantamkoc.microservicetraining.healthcare.physician.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface PhysicianRepository extends JpaRepository<PhysicianEntity, Long> {
    List<PhysicianEntity> findByTenantId(Long tenantId);
}

