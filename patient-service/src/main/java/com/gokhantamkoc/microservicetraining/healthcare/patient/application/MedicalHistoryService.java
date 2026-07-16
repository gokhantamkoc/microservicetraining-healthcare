package com.gokhantamkoc.microservicetraining.healthcare.patient.application;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gokhantamkoc.microservicetraining.healthcare.patient.adapter.out.persistence.MedicalHistoryPersistenceAdapter;
import com.gokhantamkoc.microservicetraining.healthcare.patient.domain.MedicalHistoryEntry;

@Service
@Transactional
public class MedicalHistoryService implements MedicalHistoryUseCase {
    private final MedicalHistoryPersistenceAdapter persistence;

    public MedicalHistoryService(MedicalHistoryPersistenceAdapter persistence) {
        this.persistence = persistence;
    }

    @Override
    public MedicalHistoryEntry addHistoryEntry(Long patientId, String summary) {
        return persistence.save(new MedicalHistoryEntry(null, patientId, summary, Instant.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicalHistoryEntry> listHistory(Long patientId) {
        return persistence.findByPatientId(patientId);
    }
}

