package com.gokhantamkoc.microservicetraining.healthcare.patient.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gokhantamkoc.microservicetraining.healthcare.patient.domain.MedicalHistoryEntry;

@Component
public class MedicalHistoryPersistenceAdapter {
    private final MedicalHistoryRepository repository;

    public MedicalHistoryPersistenceAdapter(MedicalHistoryRepository repository) {
        this.repository = repository;
    }

    public MedicalHistoryEntry save(MedicalHistoryEntry entry) {
        MedicalHistoryEntity entity = new MedicalHistoryEntity();
        entity.patientId = entry.patientId();
        entity.summary = entry.summary();
        entity.occurredAt = entry.occurredAt();
        return toDomain(repository.save(entity));
    }

    public List<MedicalHistoryEntry> findByPatientId(Long patientId) {
        return repository.findByPatientIdOrderByOccurredAtDesc(patientId).stream().map(this::toDomain).toList();
    }

    private MedicalHistoryEntry toDomain(MedicalHistoryEntity entity) {
        return new MedicalHistoryEntry(entity.id, entity.patientId, entity.summary, entity.occurredAt);
    }
}

