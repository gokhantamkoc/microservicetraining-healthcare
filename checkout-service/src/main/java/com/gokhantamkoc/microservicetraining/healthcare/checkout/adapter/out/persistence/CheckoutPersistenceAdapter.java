package com.gokhantamkoc.microservicetraining.healthcare.checkout.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.checkout.domain.CheckoutSession;
import com.gokhantamkoc.microservicetraining.healthcare.checkout.domain.CheckoutStatus;

@Component
public class CheckoutPersistenceAdapter {
    private final CheckoutRepository repository;

    public CheckoutPersistenceAdapter(CheckoutRepository repository) {
        this.repository = repository;
    }

    public CheckoutSession save(CheckoutSession session) {
        CheckoutEntity entity = new CheckoutEntity();
        entity.tenantId = session.tenantId();
        entity.patientId = session.patientId();
        entity.amount = session.amount();
        entity.status = session.status();
        return toDomain(repository.save(entity));
    }

    public CheckoutSession updateStatus(Long id, CheckoutStatus status) {
        CheckoutEntity entity = repository.findById(id).orElseThrow();
        entity.status = status;
        return toDomain(repository.save(entity));
    }

    public Optional<CheckoutSession> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    private CheckoutSession toDomain(CheckoutEntity entity) {
        return new CheckoutSession(entity.id, entity.tenantId, entity.patientId, entity.amount, entity.status);
    }
}

