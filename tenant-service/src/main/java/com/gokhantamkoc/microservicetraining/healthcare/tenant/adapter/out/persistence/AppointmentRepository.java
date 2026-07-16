package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

interface AppointmentRepository extends MongoRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByTenantIdOrderByStartsAtAsc(Long tenantId);
    Optional<AppointmentEntity> findTopByOrderByIdDesc();
}
