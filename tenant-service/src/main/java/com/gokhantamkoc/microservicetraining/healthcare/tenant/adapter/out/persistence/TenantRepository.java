package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

interface TenantRepository extends MongoRepository<TenantEntity, Long> {
    Optional<TenantEntity> findTopByOrderByIdDesc();
}
