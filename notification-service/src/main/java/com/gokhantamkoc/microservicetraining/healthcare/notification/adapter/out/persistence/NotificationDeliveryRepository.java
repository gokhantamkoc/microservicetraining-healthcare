package com.gokhantamkoc.microservicetraining.healthcare.notification.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

interface NotificationDeliveryRepository extends MongoRepository<NotificationDeliveryEntity, Long> {
    List<NotificationDeliveryEntity> findByTenantId(Long tenantId);
    Optional<NotificationDeliveryEntity> findTopByOrderByIdDesc();
}
