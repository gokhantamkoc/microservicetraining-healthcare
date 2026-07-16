package com.gokhantamkoc.microservicetraining.healthcare.notification.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.notification.domain.NotificationDelivery;

@Component
public class NotificationPersistenceAdapter {
    private final NotificationDeliveryRepository repository;

    public NotificationPersistenceAdapter(NotificationDeliveryRepository repository) {
        this.repository = repository;
    }

    public NotificationDelivery save(NotificationDelivery delivery) {
        NotificationDeliveryEntity entity = new NotificationDeliveryEntity();
        entity.id = delivery.id() == null ? nextId() : delivery.id();
        entity.tenantId = delivery.tenantId();
        entity.patientId = delivery.patientId();
        entity.physicianId = delivery.physicianId();
        entity.provider = delivery.provider();
        entity.message = delivery.message();
        entity.deliveredAt = delivery.deliveredAt();
        return toDomain(repository.save(entity));
    }

    public List<NotificationDelivery> findByTenantId(Long tenantId) {
        return repository.findByTenantId(tenantId).stream().map(this::toDomain).toList();
    }

    private NotificationDelivery toDomain(NotificationDeliveryEntity entity) {
        return new NotificationDelivery(entity.id, entity.tenantId, entity.patientId, entity.physicianId, entity.provider, entity.message, entity.deliveredAt);
    }

    private Long nextId() {
        return repository.findTopByOrderByIdDesc().map(entity -> entity.id + 1).orElse(1L);
    }
}
