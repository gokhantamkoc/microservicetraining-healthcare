package com.gokhantamkoc.microservicetraining.healthcare.tenant.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

interface AnnouncementRepository extends MongoRepository<AnnouncementEntity, Long> {
    List<AnnouncementEntity> findByTenantIdOrderByPublishedAtDesc(Long tenantId);
    Optional<AnnouncementEntity> findTopByOrderByIdDesc();
}
